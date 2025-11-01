package com.dvtsoftware.airline.booking.handler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import com.dvtsoftware.airline.booking.model.Booking;
import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.model.Passenger;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class BookingsHandler {
    private DatabaseAppService databaseAppService;

    public BookingsHandler(DatabaseAppService databaseService) {
        this.databaseAppService = databaseService;
    }
    
	public void createBooking(RoutingContext ctx) {
		ctx.request().bodyHandler(body -> {
			JsonObject requestBody = body.toJsonObject();
			if (requestBody == null ||
				requestBody.getLong("passengerId") == null ||
				requestBody.getLong("flightId") == null ||
				requestBody.getString("seatNumber") == null) {
				ctx.response()
					.setStatusCode(400)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Missing required fields: passengerId, flightId, seatNumber").encode());
				return;
			}

			Long passengerId = requestBody.getLong("passengerId");
			Long flightId = requestBody.getLong("flightId");
			String seatNumber = requestBody.getString("seatNumber");

			// Validate passenger exists
			databaseAppService.getPassengerById(passengerId).compose(passenger -> {
				if (passenger == null) {
					return io.vertx.core.Future.failedFuture("Passenger not found");
				}
				
				// Validate flight exists and has available seats
				return databaseAppService.getFlightById(flightId).compose(flight -> {
					if (flight == null) {
						return io.vertx.core.Future.failedFuture("Flight not found");
					}
					
					if (flight.getAvailableSeats() <= 0) {
						return io.vertx.core.Future.failedFuture("No available seats on this flight");
					}
					
					// Generate booking reference
					String bookingReference = generateBookingReference(flight.getFlightNumber());
					String status = "CONFIRMED";
					BigDecimal totalAmount = flight.getPrice();
					
					// Create booking
					return databaseAppService.createBooking(bookingReference, passengerId, flightId, 
															seatNumber, status, totalAmount)
						.compose(booking -> {
							// Update available seats
							int newAvailableSeats = flight.getAvailableSeats() - 1;
							return databaseAppService.updateFlightAvailableSeats(flightId, newAvailableSeats)
								.map(updated -> booking);
						});
				});
			}).onComplete(ar -> {
				if (ar.succeeded()) {
					Booking booking = ar.result();
					JsonObject response = new JsonObject()
						.put("id", booking.getId())
						.put("bookingReference", booking.getBookingReference())
						.put("passengerId", booking.getPassengerId())
						.put("flightId", booking.getFlightId())
						.put("seatNumber", booking.getSeatNumber())
						.put("status", booking.getStatus())
						.put("totalAmount", booking.getTotalAmount())
						.put("bookingDate", booking.getBookingDate() != null ? booking.getBookingDate().toString() : null);
					ctx.response()
						.setStatusCode(201)
						.putHeader("Content-Type", "application/json")
						.end(response.encode());
				} else {
					ctx.response()
						.setStatusCode(400)
						.putHeader("Content-Type", "application/json")
						.end(new JsonObject().put("error", ar.cause().getMessage()).encode());
				}
			});
		});
	}

	public void getBookingById(RoutingContext ctx) {
		String idParam = ctx.pathParam("id");
		if (idParam == null) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Missing booking ID").encode());
			return;
		}

		Long id;
		try {
			id = Long.parseLong(idParam);
		} catch (NumberFormatException e) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Invalid booking ID format").encode());
			return;
		}

		databaseAppService.getBookingById(id).onComplete(ar -> {
			if (ar.succeeded()) {
				Booking booking = ar.result();
				if (booking != null) {
					JsonObject response = new JsonObject()
						.put("id", booking.getId())
						.put("bookingReference", booking.getBookingReference())
						.put("passengerId", booking.getPassengerId())
						.put("flightId", booking.getFlightId())
						.put("seatNumber", booking.getSeatNumber())
						.put("status", booking.getStatus())
						.put("totalAmount", booking.getTotalAmount())
						.put("bookingDate", booking.getBookingDate() != null ? booking.getBookingDate().toString() : null)
						.put("createdAt", booking.getCreatedAt() != null ? booking.getCreatedAt().toString() : null)
						.put("updatedAt", booking.getUpdatedAt() != null ? booking.getUpdatedAt().toString() : null);
					ctx.response()
						.setStatusCode(200)
						.putHeader("Content-Type", "application/json")
						.end(response.encode());
				} else {
					ctx.response()
						.setStatusCode(404)
						.putHeader("Content-Type", "application/json")
						.end(new JsonObject().put("error", "Booking not found").encode());
				}
			} else {
				ctx.response()
					.setStatusCode(500)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", "Database error: " + ar.cause().getMessage()).encode());
			}
		});
	}

	public void cancelBooking(RoutingContext ctx) {
		String idParam = ctx.pathParam("id");
		if (idParam == null) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Missing booking ID").encode());
			return;
		}

		Long id;
		try {
			id = Long.parseLong(idParam);
		} catch (NumberFormatException e) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Invalid booking ID format").encode());
			return;
		}

		// First get the booking to find the flight
		databaseAppService.getBookingById(id).compose(booking -> {
			if (booking == null) {
				return io.vertx.core.Future.failedFuture("Booking not found");
			}
			
			// Get flight to update available seats
			return databaseAppService.getFlightById(booking.getFlightId())
				.compose(flight -> {
					if (flight == null) {
						return io.vertx.core.Future.failedFuture("Flight not found");
					}
					
					// Cancel booking
					return databaseAppService.cancelBooking(id)
						.compose(cancelled -> {
							if (!cancelled) {
								return io.vertx.core.Future.failedFuture("Failed to cancel booking");
							}
							
							// Update available seats
							int newAvailableSeats = flight.getAvailableSeats() + 1;
							return databaseAppService.updateFlightAvailableSeats(booking.getFlightId(), newAvailableSeats)
								.map(updated -> "Booking cancelled successfully");
						});
				});
		}).onComplete(ar -> {
			if (ar.succeeded()) {
				ctx.response()
					.setStatusCode(200)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("message", ar.result()).encode());
			} else {
				ctx.response()
					.setStatusCode(400)
					.putHeader("Content-Type", "application/json")
					.end(new JsonObject().put("error", ar.cause().getMessage()).encode());
			}
		});
	}

	public void listPassengerBookings(RoutingContext ctx) {
		String idParam = ctx.pathParam("id");
		if (idParam == null) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Missing passenger ID").encode());
			return;
		}

		Long id;
		try {
			id = Long.parseLong(idParam);
		} catch (NumberFormatException e) {
			ctx.response()
				.setStatusCode(400)
				.putHeader("Content-Type", "application/json")
				.end(new JsonObject().put("error", "Invalid passenger ID format").encode());
			return;
		}

		databaseAppService.getBookingsByPassengerId(id).onComplete(ar -> {
			if (ar.succeeded()) {
				List<Booking> bookings = ar.result();
				JsonArray jsonArray = new JsonArray();
				
				for (Booking booking : bookings) {
					JsonObject bookingJson = new JsonObject()
						.put("id", booking.getId())
						.put("bookingReference", booking.getBookingReference())
						.put("passengerId", booking.getPassengerId())
						.put("flightId", booking.getFlightId())
						.put("seatNumber", booking.getSeatNumber())
						.put("status", booking.getStatus())
						.put("totalAmount", booking.getTotalAmount())
						.put("bookingDate", booking.getBookingDate() != null ? booking.getBookingDate().toString() : null);
					jsonArray.add(bookingJson);
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

	private String generateBookingReference(String flightNumber) {
		// Generate a unique booking reference using flight number and timestamp
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		return flightNumber + timestamp + uuid;
	}
}
