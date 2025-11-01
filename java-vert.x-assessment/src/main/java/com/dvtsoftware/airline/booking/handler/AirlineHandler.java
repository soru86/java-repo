package com.dvtsoftware.airline.booking.handler;

import java.util.List;

import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Tuple;

public class AirlineHandler {
    private DatabaseAppService databaseAppService;

    public AirlineHandler(DatabaseAppService databaseService) {
        this.databaseAppService = databaseService;
    }

    public void addAirline(RoutingContext ctx) {
        ctx.request().bodyHandler(body -> {
            JsonObject requestBody = body.toJsonObject();
            if (requestBody == null ||
                requestBody.getString("code") == null ||
                requestBody.getString("name") == null ||
                requestBody.getString("country") == null) {
                ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(new JsonObject().put("error", "Missing required fields").encode());
                return;
            }

            String code = requestBody.getString("code");
            String name = requestBody.getString("name");
            String country = requestBody.getString("country");

            String sql = "INSERT INTO airlines (code, name, country) VALUES (?, ?, ?)";

            databaseAppService.getClient()
                .preparedQuery(sql)
                .execute(Tuple.of(code, name, country))
                .onComplete(ar -> {
                    if (ar.succeeded()) {
                        // For H2, we can use a different approach to get the inserted ID
                        // Let's query the last inserted airline by code
                        String selectSql = "SELECT * FROM airlines WHERE code = ? ORDER BY id DESC LIMIT 1";
                        databaseAppService.getClient()
                            .preparedQuery(selectSql)
                            .execute(Tuple.of(code))
                            .onComplete(selectAr -> {
                                if (selectAr.succeeded()) {
                                    io.vertx.sqlclient.RowSet<io.vertx.sqlclient.Row> rows = selectAr.result();
                                    if (rows.iterator().hasNext()) {
                                        io.vertx.sqlclient.Row row = rows.iterator().next();
                                        JsonObject response = new JsonObject()
                                            .put("id", row.getLong("id"))
                                            .put("code", row.getString("code"))
                                            .put("name", row.getString("name"))
                                            .put("country", row.getString("country"));
                                        ctx.response()
                                            .setStatusCode(201)
                                            .putHeader("Content-Type", "application/json")
                                            .end(response.encode());
                                    } else {
                                        ctx.response()
                                            .setStatusCode(500)
                                            .putHeader("Content-Type", "application/json")
                                            .end(new JsonObject().put("error", "Failed to retrieve created airline").encode());
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
    
    public void listAirlines(RoutingContext ctx) {
        databaseAppService.getAllAirlines().onComplete(ar -> {
            if (ar.succeeded()) {
                List<Airline> airlines = ar.result();
                JsonArray jsonArray = new JsonArray();
                
                for (Airline airline : airlines) {
                    JsonObject airlineJson = new JsonObject()
                        .put("id", airline.getId())
                        .put("code", airline.getCode())
                        .put("name", airline.getName())
                        .put("country", airline.getCountry())
                        .put("createdAt", airline.getCreatedAt() != null ? airline.getCreatedAt().toString() : null)
                        .put("updatedAt", airline.getUpdatedAt() != null ? airline.getUpdatedAt().toString() : null);
                    jsonArray.add(airlineJson);
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