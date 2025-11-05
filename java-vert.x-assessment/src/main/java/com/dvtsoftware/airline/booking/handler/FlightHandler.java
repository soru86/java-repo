package com.dvtsoftware.airline.booking.handler;

import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * HTTP request handler for flight operations.
 */
public class FlightHandler {
    private final IDatabaseService databaseService;
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FlightHandler(IDatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Handle POST /flights - Create a new flight
     */
    public void createFlight(RoutingContext context) {
        try {
            JsonObject body = context.body().asJsonObject();
            if (body == null || body.isEmpty()) {
                sendError(context, 400, "Request body is required");
                return;
            }

            Long airlineId = body.getLong("airlineId");
            String flightNumber = body.getString("flightNumber");
            String origin = body.getString("origin");
            String destination = body.getString("destination");
            String departureTimeStr = body.getString("departureTime");
            String arrivalTimeStr = body.getString("arrivalTime");
            Integer totalSeats = body.getInteger("totalSeats");
            Integer availableSeats = body.getInteger("availableSeats");
            BigDecimal price = null;
            if (body.containsKey("price")) {
                Object priceObj = body.getValue("price");
                if (priceObj instanceof Number) {
                    price = BigDecimal.valueOf(((Number) priceObj).doubleValue());
                } else if (priceObj instanceof String) {
                    price = new BigDecimal((String) priceObj);
                }
            }

            if (airlineId == null || flightNumber == null || origin == null || destination == null ||
                    departureTimeStr == null || arrivalTimeStr == null || totalSeats == null ||
                    availableSeats == null || price == null) {
                sendError(context, 400, "Missing required fields");
                return;
            }

            LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, DATETIME_FORMATTER);
            LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr, DATETIME_FORMATTER);

            Flight flight = new Flight(airlineId, flightNumber, origin, destination,
                    departureTime, arrivalTime, totalSeats, availableSeats, price);

            databaseService.createFlight(flight)
                    .onSuccess(createdFlight -> {
                        context.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(JsonObject.mapFrom(createdFlight).encodePrettily());
                    })
                    .onFailure(error -> {
                        sendError(context, 500, "Failed to create flight: " + error.getMessage());
                    });
        } catch (Exception e) {
            sendError(context, 400, "Invalid request: " + e.getMessage());
        }
    }

    /**
     * Handle GET /flights/{id} - Get flight by ID
     */
    public void getFlightById(RoutingContext context) {
        try {
            Long id = Long.parseLong(context.pathParam("id"));
            databaseService.getFlightById(id)
                    .onSuccess(flight -> {
                        if (flight == null) {
                            sendError(context, 404, "Flight not found");
                        } else {
                            context.response()
                                    .setStatusCode(200)
                                    .putHeader("Content-Type", "application/json")
                                    .end(JsonObject.mapFrom(flight).encodePrettily());
                        }
                    })
                    .onFailure(error -> {
                        sendError(context, 500, "Failed to retrieve flight: " + error.getMessage());
                    });
        } catch (NumberFormatException e) {
            sendError(context, 400, "Invalid flight ID format");
        }
    }

    /**
     * Handle GET /flights/search?from=X&to=Y - Search flights by route
     */
    public void searchFlights(RoutingContext context) {
        String from = context.queryParams().get("from");
        String to = context.queryParams().get("to");

        if (from == null || to == null) {
            sendError(context, 400, "Missing required query parameters: from, to");
            return;
        }

        databaseService.searchFlights(from, to)
                .onSuccess(flights -> {
                    JsonObject response = new JsonObject()
                            .put("flights", flights)
                            .put("count", flights.size());
                    context.response()
                            .setStatusCode(200)
                            .putHeader("Content-Type", "application/json")
                            .end(response.encodePrettily());
                })
                .onFailure(error -> {
                    sendError(context, 500, "Failed to search flights: " + error.getMessage());
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

