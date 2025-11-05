package com.dvtsoftware.airline.booking.handler;

import com.dvtsoftware.airline.booking.model.Passenger;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * HTTP request handler for passenger operations.
 */
public class PassengerHandler {
    private final IDatabaseService databaseService;

    public PassengerHandler(IDatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Handle POST /passengers - Create a new passenger
     */
    public void createPassenger(RoutingContext context) {
        try {
            JsonObject body = context.body().asJsonObject();
            if (body == null || body.isEmpty()) {
                sendError(context, 400, "Request body is required");
                return;
            }

            String firstName = body.getString("firstName");
            String lastName = body.getString("lastName");
            String email = body.getString("email");
            String phone = body.getString("phone");
            String passportNumber = body.getString("passportNumber");
            String nationality = body.getString("nationality");

            if (firstName == null || lastName == null || email == null) {
                sendError(context, 400, "Missing required fields: firstName, lastName, email");
                return;
            }

            Passenger passenger = new Passenger(firstName, lastName, email, phone, passportNumber, nationality);

            databaseService.createPassenger(passenger)
                    .onSuccess(createdPassenger -> {
                        context.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(JsonObject.mapFrom(createdPassenger).encodePrettily());
                    })
                    .onFailure(error -> {
                        String errorMessage = error.getMessage();
                        if (errorMessage != null && errorMessage.contains("unique") || errorMessage.contains("duplicate")) {
                            sendError(context, 409, "Passenger with this email already exists");
                        } else {
                            sendError(context, 500, "Failed to create passenger: " + errorMessage);
                        }
                    });
        } catch (Exception e) {
            sendError(context, 400, "Invalid request: " + e.getMessage());
        }
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

