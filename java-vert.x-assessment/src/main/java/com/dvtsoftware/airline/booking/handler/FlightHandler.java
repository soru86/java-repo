package com.dvtsoftware.airline.booking.handler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Tuple;

public class FlightHandler {
    private DatabaseAppService databaseAppService;

    public FlightHandler(DatabaseAppService databaseService) {
        this.databaseAppService = databaseService;
    }
    
	public void addFlight(RoutingContext ctx) {
		ctx.request().bodyHandler(body -> {
			JsonObject requestBody = body.toJsonObject();
			if (requestBody == null ||
				requestBody.getString("flightNumber") == null ||
				requestBody.getLong("airlineId") == null ||
				requestBody.getString("departureAirport") == null ||
				requestBody.getString("arrivalAirport") == null ||
				requestBody.getString("departureTime") == null ||
				requestBody.getString("arrivalTime") == null ||
				requestBody.getInteger("totalSeats") == null ||
				requestBody.getInteger("availableSeats") == null ||
				requestBody.getValue("price") == null) {
				ctx.response()
					.setStatusCode(400)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Missing required fields").encode());
				return;
			}

			String flightNumber = requestBody.getString("flightNumber");
			Long airlineId = requestBody.getLong("airlineId");
			String departureAirport = requestBody.getString("departureAirport");
			String arrivalAirport = requestBody.getString("arrivalAirport");
			String departureTimeStr = requestBody.getString("departureTime");
			String arrivalTimeStr = requestBody.getString("arrivalTime");
			Integer totalSeats = requestBody.getInteger("totalSeats");
			Integer availableSeats = requestBody.getInteger("availableSeats");
			Object priceObj = requestBody.getValue("price");
			BigDecimal price;
			if (priceObj instanceof Number) {
				price = BigDecimal.valueOf(((Number) priceObj).doubleValue());
			} else {
				price = new BigDecimal(priceObj.toString());
			}
			String status = requestBody.getString("status", "SCHEDULED");

			// Parse datetime strings
			LocalDateTime departureTime;
			LocalDateTime arrivalTime;
			try {
				departureTime = LocalDateTime.parse(departureTimeStr);
				arrivalTime = LocalDateTime.parse(arrivalTimeStr);
			} catch (Exception e) {
				ctx.response()
					.setStatusCode(400)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Invalid datetime format").encode());
				return;
			}

			String sql = "INSERT INTO flights (flight_number, airline_id, departure_airport, arrival_airport, " +
						"departure_time, arrival_time, available_seats, total_seats, price, status) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			databaseAppService.getClient()
				.preparedQuery(sql)
				.execute(Tuple.of(flightNumber, airlineId, departureAirport, arrivalAirport,
								departureTime, arrivalTime, availableSeats, totalSeats, price, status))
				.onComplete(ar -> {
					if (ar.succeeded()) {
						// For H2, we can use a different approach to get the inserted ID
						// Let's query the last inserted flight by flight number
						String selectSql = "SELECT * FROM flights WHERE flight_number = ? ORDER BY id DESC LIMIT 1";
						databaseAppService.getClient()
							.preparedQuery(selectSql)
							.execute(Tuple.of(flightNumber))
							.onComplete(selectAr -> {
								if (selectAr.succeeded()) {
									io.vertx.sqlclient.RowSet<io.vertx.sqlclient.Row> rows = selectAr.result();
									if (rows.iterator().hasNext()) {
										io.vertx.sqlclient.Row row = rows.iterator().next();
										JsonObject response = new JsonObject()
											.put("id", row.getLong("id"))
											.put("flightNumber", row.getString("flight_number"))
											.put("airlineId", row.getLong("airline_id"))
											.put("departureAirport", row.getString("departure_airport"))
											.put("arrivalAirport", row.getString("arrival_airport"))
											.put("departureTime", row.getLocalDateTime("departure_time").toString())
											.put("arrivalTime", row.getLocalDateTime("arrival_time").toString())
											.put("availableSeats", row.getInteger("available_seats"))
											.put("totalSeats", row.getInteger("total_seats"))
											.put("price", row.getBigDecimal("price"))
											.put("status", row.getString("status"));
										ctx.response()
											.setStatusCode(201)
											.putHeader("Content-Type", "application/json")
											.end(response.encode());
									} else {
										ctx.response()
											.setStatusCode(500)
											.putHeader("Content-Type", "application/json")
											.end(new JsonObject().put("error", "Failed to retrieve created flight").encode());
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

	public void getFlightById(RoutingContext ctx) {
		String idParam = ctx.pathParam("id");
		if (idParam == null) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Missing flight ID").encode());
			return;
		}

		Long id;
		try {
			id = Long.parseLong(idParam);
		} catch (NumberFormatException e) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Invalid flight ID format").encode());
			return;
		}

		databaseAppService.getFlightById(id).onComplete(ar -> {
			if (ar.succeeded()) {
				Flight flight = ar.result();
				if (flight != null) {
					JsonObject response = new JsonObject()
						.put("id", flight.getId())
						.put("flightNumber", flight.getFlightNumber())
						.put("airlineId", flight.getAirlineId())
						.put("departureAirport", flight.getDepartureAirport())
						.put("arrivalAirport", flight.getArrivalAirport())
						.put("departureTime", flight.getDepartureTime() != null ? flight.getDepartureTime().toString() : null)
						.put("arrivalTime", flight.getArrivalTime() != null ? flight.getArrivalTime().toString() : null)
						.put("availableSeats", flight.getAvailableSeats())
						.put("totalSeats", flight.getTotalSeats())
						.put("price", flight.getPrice())
						.put("status", flight.getStatus())
						.put("createdAt", flight.getCreatedAt() != null ? flight.getCreatedAt().toString() : null)
						.put("updatedAt", flight.getUpdatedAt() != null ? flight.getUpdatedAt().toString() : null);
					ctx.response()
						.setStatusCode(200)
						.putHeader("Content-Type", "application/json")
						.end(response.encode());
				} else {
					ctx.response()
						.setStatusCode(404)
						.putHeader("Content-Type", "application/json")
						.end(new JsonObject().put("error", "Flight not found").encode());
				}
			} else {
				ctx.response()
					.setStatusCode(500)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Database error: " + ar.cause().getMessage()).encode());
			}
		});
	}

	public void searchFlights(RoutingContext ctx) {
		String from = ctx.request().getParam("from");
		String to = ctx.request().getParam("to");

		if (from == null || to == null) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Missing 'from' or 'to' query parameters").encode());
			return;
		}

		databaseAppService.searchFlightsByRoute(from, to).onComplete(ar -> {
			if (ar.succeeded()) {
				List<Flight> flights = ar.result();
				JsonArray jsonArray = new JsonArray();
				
				for (Flight flight : flights) {
					JsonObject flightJson = new JsonObject()
						.put("id", flight.getId())
						.put("flightNumber", flight.getFlightNumber())
						.put("airlineId", flight.getAirlineId())
						.put("departureAirport", flight.getDepartureAirport())
						.put("arrivalAirport", flight.getArrivalAirport())
						.put("departureTime", flight.getDepartureTime() != null ? flight.getDepartureTime().toString() : null)
						.put("arrivalTime", flight.getArrivalTime() != null ? flight.getArrivalTime().toString() : null)
						.put("availableSeats", flight.getAvailableSeats())
						.put("totalSeats", flight.getTotalSeats())
						.put("price", flight.getPrice())
						.put("status", flight.getStatus());
					jsonArray.add(flightJson);
				}
				
				ctx.response()
					.setStatusCode(200)
					.putHeader("Content-Type", "application/json")
					.end(jsonArray.encode());
			} else {
				ctx.response()
					.setStatusCode(500)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Database error: " + ar.cause().getMessage()).encode());
			}
		});
	}
}
