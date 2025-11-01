package com.dvtsoftware.airline.booking.handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Tuple;

public class PassengerHandler {
    private DatabaseAppService databaseAppService;

    public PassengerHandler(DatabaseAppService databaseService) {
        this.databaseAppService = databaseService;
    }
    
	public void addPassenger(RoutingContext ctx) {
		ctx.request().bodyHandler(body -> {
			JsonObject requestBody = body.toJsonObject();
			if (requestBody == null ||
				requestBody.getString("firstName") == null ||
				requestBody.getString("lastName") == null ||
				requestBody.getString("email") == null) {
				ctx.response()
					.setStatusCode(400)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Missing required fields: firstName, lastName, email").encode());
				return;
			}

			String firstName = requestBody.getString("firstName");
			String lastName = requestBody.getString("lastName");
			String email = requestBody.getString("email");
			String phone = requestBody.getString("phone");
			String passportNumber = requestBody.getString("passportNumber");
			String dateOfBirthStr = requestBody.getString("dateOfBirth");

			// Parse date of birth if provided
			LocalDate dateOfBirth = null;
			if (dateOfBirthStr != null && !dateOfBirthStr.isEmpty()) {
				try {
					dateOfBirth = LocalDate.parse(dateOfBirthStr);
				} catch (Exception e) {
					ctx.response()
						.setStatusCode(400)
						.putHeader("Content-Type", "application/json")
						.end(new JsonObject().put("error", "Invalid date format for dateOfBirth").encode());
					return;
				}
			}

			String sql = "INSERT INTO passengers (first_name, last_name, email, phone, passport_number, date_of_birth) " +
						"VALUES (?, ?, ?, ?, ?, ?)";

			databaseAppService.getClient()
				.preparedQuery(sql)
				.execute(Tuple.of(firstName, lastName, email, phone, passportNumber, dateOfBirth))
				.onComplete(ar -> {
					if (ar.succeeded()) {
						// For H2, we can use a different approach to get the inserted ID
						// Let's query the last inserted passenger by email
						String selectSql = "SELECT * FROM passengers WHERE email = ? ORDER BY id DESC LIMIT 1";
						databaseAppService.getClient()
							.preparedQuery(selectSql)
							.execute(Tuple.of(email))
							.onComplete(selectAr -> {
								if (selectAr.succeeded()) {
									io.vertx.sqlclient.RowSet<io.vertx.sqlclient.Row> rows = selectAr.result();
									if (rows.iterator().hasNext()) {
										io.vertx.sqlclient.Row row = rows.iterator().next();
										JsonObject response = new JsonObject()
											.put("id", row.getLong("id"))
											.put("firstName", row.getString("first_name"))
											.put("lastName", row.getString("last_name"))
											.put("email", row.getString("email"))
											.put("phone", row.getString("phone"))
											.put("passportNumber", row.getString("passport_number"))
											.put("dateOfBirth", row.getLocalDate("date_of_birth") != null ? row.getLocalDate("date_of_birth").toString() : null);
										ctx.response()
											.setStatusCode(201)
											.putHeader("Content-Type", "application/json")
											.end(response.encode());
									} else {
										ctx.response()
											.setStatusCode(500)
											.putHeader("Content-Type", "application/json")
											.end(new JsonObject().put("error", "Failed to retrieve created passenger").encode());
									}
								} else {
									ctx.response()
										.setStatusCode(500)
										.putHeader("Content-Type", "application/json")
										.end(new JsonObject().put("error", "Database error: " + selectAr.cause().getMessage()).encode());
								}
							});
					} else {
						ctx.response()
							.setStatusCode(500)
							.putHeader("Content-Type", "application/json")
							.end(new JsonObject().put("error", "Database error: " + ar.cause().getMessage()).encode());
					}
				});
		});
	}
}
