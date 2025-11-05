package com.dvtsoftware.airline.booking.handler;

import com.dvtsoftware.airline.booking.model.Booking;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * HTTP request handler for booking operations.
 */
public class BookingsHandler {
    private final IDatabaseService databaseService;

    public BookingsHandler(IDatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Handle POST /bookings - Create a new booking
     */
    public void createBooking(RoutingContext context) {
        try {
            JsonObject body = context.body().asJsonObject();
            if (body == null || body.isEmpty()) {
                sendError(context, 400, "Request body is required");
                return;
            }

            Long passengerId = body.getLong("passengerId");
            Long flightId = body.getLong("flightId");
            String seatNumber = body.getString("seatNumber");

            if (passengerId == null || flightId == null) {
                sendError(context, 400, "Missing required fields: passengerId, flightId");
                return;
            }

            Booking booking = new Booking(passengerId, flightId, null, seatNumber, "CONFIRMED");

            databaseService.createBooking(booking)
                    .onSuccess(createdBooking -> {
                        context.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(JsonObject.mapFrom(createdBooking).encodePrettily());
                    })
                    .onFailure(error -> {
                        String errorMessage = error.getMessage();
                        int statusCode = 500;
                        if (errorMessage != null) {
                            if (errorMessage.contains("not found")) {
                                statusCode = 404;
                            } else if (errorMessage.contains("No available seats")) {
                                statusCode = 409;
                            }
                        }
                        sendError(context, statusCode, errorMessage != null ? errorMessage : "Failed to create booking");
                    });
        } catch (Exception e) {
            sendError(context, 400, "Invalid request: " + e.getMessage());
        }
    }

    /**
     * Handle GET /bookings/{id} - Get booking by ID
     */
    public void getBookingById(RoutingContext context) {
        try {
            Long id = Long.parseLong(context.pathParam("id"));
            databaseService.getBookingById(id)
                    .onSuccess(booking -> {
                        if (booking == null) {
                            sendError(context, 404, "Booking not found");
                        } else {
                            context.response()
                                    .setStatusCode(200)
                                    .putHeader("Content-Type", "application/json")
                                    .end(JsonObject.mapFrom(booking).encodePrettily());
                        }
                    })
                    .onFailure(error -> {
                        sendError(context, 500, "Failed to retrieve booking: " + error.getMessage());
                    });
        } catch (NumberFormatException e) {
            sendError(context, 400, "Invalid booking ID format");
        }
    }

    /**
     * Handle DELETE /bookings/{id} - Cancel a booking
     */
    public void cancelBooking(RoutingContext context) {
        try {
            Long id = Long.parseLong(context.pathParam("id"));
            databaseService.cancelBooking(id)
                    .onSuccess(v -> {
                        JsonObject response = new JsonObject()
                                .put("message", "Booking cancelled successfully")
                                .put("bookingId", id);
                        context.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(response.encodePrettily());
                    })
                    .onFailure(error -> {
                        String errorMessage = error.getMessage();
                        int statusCode = 500;
                        if (errorMessage != null) {
                            if (errorMessage.contains("not found")) {
                                statusCode = 404;
                            } else if (errorMessage.contains("already cancelled")) {
                                statusCode = 409;
                            }
                        }
                        sendError(context, statusCode, errorMessage != null ? errorMessage : "Failed to cancel booking");
                    });
        } catch (NumberFormatException e) {
            sendError(context, 400, "Invalid booking ID format");
        }
    }

    /**
     * Handle GET /passengers/{id}/bookings - Get all bookings for a passenger
     */
    public void getBookingsByPassengerId(RoutingContext context) {
        try {
            Long passengerId = Long.parseLong(context.pathParam("id"));
            databaseService.getBookingsByPassengerId(passengerId)
                    .onSuccess(bookings -> {
                        JsonObject response = new JsonObject()
                                .put("bookings", bookings)
                                .put("count", bookings.size());
                        context.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(response.encodePrettily());
                    })
                    .onFailure(error -> {
                        sendError(context, 500, "Failed to retrieve bookings: " + error.getMessage());
                    });
        } catch (NumberFormatException e) {
            sendError(context, 400, "Invalid passenger ID format");
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

