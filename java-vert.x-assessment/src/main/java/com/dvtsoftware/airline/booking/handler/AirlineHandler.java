package com.dvtsoftware.airline.booking.handler;

import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * HTTP request handler for airline operations.
 */
public class AirlineHandler {
    private final IDatabaseService databaseService;

    public AirlineHandler(IDatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Handle POST /airlines - Create a new airline
     */
    public void createAirline(RoutingContext context) {
        try {
            JsonObject body = context.body().asJsonObject();
            if (body == null || body.isEmpty()) {
                sendError(context, 400, "Request body is required");
                return;
            }

            String code = body.getString("code");
            String name = body.getString("name");
            String country = body.getString("country");

            if (code == null || name == null || country == null) {
                sendError(context, 400, "Missing required fields: code, name, country");
                return;
            }

            Airline airline = new Airline(code, name, country);
            databaseService.createAirline(airline)
                    .onSuccess(createdAirline -> {
                        context.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(JsonObject.mapFrom(createdAirline).encodePrettily());
                    })
                    .onFailure(error -> {
                        sendError(context, 500, "Failed to create airline: " + error.getMessage());
                    });
        } catch (Exception e) {
            sendError(context, 400, "Invalid request: " + e.getMessage());
        }
    }

    /**
     * Handle GET /airlines - Get all airlines
     */
    public void getAllAirlines(RoutingContext context) {
        databaseService.getAllAirlines()
                .onSuccess(airlines -> {
                    JsonObject response = new JsonObject()
                            .put("airlines", airlines);
                    context.response()
                            .setStatusCode(200)
                            .putHeader("Content-Type", "application/json")
                            .end(response.encodePrettily());
                })
                .onFailure(error -> {
                    sendError(context, 500, "Failed to retrieve airlines: " + error.getMessage());
                });
    }

    private void sendError(RoutingContext context, int statusCode, String message) {
        JsonObject error = new JsonObject()
                .put("error", message)
                .put("statusCode", statusCode);
        context.response()
                .setStatusCode(statusCode)
                .putHeader("Content-Type", "application/json")
                .end(error.encodePrettily());
    }
}
