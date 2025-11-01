package com.dvtsoftware.airline.booking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.model.Booking;
import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.model.Passenger;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class DatabaseAppService extends DatabaseService {
	public DatabaseAppService(Vertx vertx) {
		super(vertx);
	}

	// Airline operations
	public Future<List<Airline>> getAllAirlines() {
		Promise<List<Airline>> promise = Promise.promise();
		String sql = "SELECT * FROM airlines ORDER BY name";
		
		client.query(sql).execute().onComplete(ar -> {
			if (ar.succeeded()) {
				List<Airline> airlines = new ArrayList<>();
				RowSet<Row> rows = ar.result();
				for (Row row : rows) {
					airlines.add(mapRowToAirline(row));
				}
				promise.complete(airlines);
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	public Future<Airline> getAirlineById(Long id) {
		Promise<Airline> promise = Promise.promise();
		String sql = "SELECT * FROM airlines WHERE id = ?";
		
		client.preparedQuery(sql).execute(Tuple.of(id)).onComplete(ar -> {
			if (ar.succeeded()) {
				RowSet<Row> rows = ar.result();
				if (rows.size() > 0) {
					promise.complete(mapRowToAirline(rows.iterator().next()));
				} else {
					promise.complete(null);
				}
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	// Flight operations
	public Future<List<Flight>> getAllFlights() {
		Promise<List<Flight>> promise = Promise.promise();
		String sql = "SELECT * FROM flights ORDER BY departure_time";
		
		client.query(sql).execute().onComplete(ar -> {
			if (ar.succeeded()) {
				List<Flight> flights = new ArrayList<>();
				RowSet<Row> rows = ar.result();
				for (Row row : rows) {
					flights.add(mapRowToFlight(row));
				}
				promise.complete(flights);
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	public Future<Flight> getFlightById(Long id) {
		Promise<Flight> promise = Promise.promise();
		String sql = "SELECT * FROM flights WHERE id = ?";
		
		client.preparedQuery(sql).execute(Tuple.of(id)).onComplete(ar -> {
			if (ar.succeeded()) {
				RowSet<Row> rows = ar.result();
				if (rows.size() > 0) {
					promise.complete(mapRowToFlight(rows.iterator().next()));
				} else {
					promise.complete(null);
				}
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	public Future<List<Flight>> searchFlightsByRoute(String from, String to) {
		Promise<List<Flight>> promise = Promise.promise();
		String sql = "SELECT * FROM flights WHERE departure_airport = ? AND arrival_airport = ? ORDER BY departure_time";
		
		client.preparedQuery(sql).execute(Tuple.of(from, to)).onComplete(ar -> {
			if (ar.succeeded()) {
				List<Flight> flights = new ArrayList<>();
				RowSet<Row> rows = ar.result();
				for (Row row : rows) {
					flights.add(mapRowToFlight(row));
				}
				promise.complete(flights);
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	// Passenger operations
	public Future<Passenger> getPassengerById(Long id) {
		Promise<Passenger> promise = Promise.promise();
		String sql = "SELECT * FROM passengers WHERE id = ?";
		
		client.preparedQuery(sql).execute(Tuple.of(id)).onComplete(ar -> {
			if (ar.succeeded()) {
				RowSet<Row> rows = ar.result();
				if (rows.size() > 0) {
					promise.complete(mapRowToPassenger(rows.iterator().next()));
				} else {
					promise.complete(null);
				}
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	// Booking operations
	public Future<Booking> getBookingById(Long id) {
		Promise<Booking> promise = Promise.promise();
		String sql = "SELECT * FROM bookings WHERE id = ?";
		
		client.preparedQuery(sql).execute(Tuple.of(id)).onComplete(ar -> {
			if (ar.succeeded()) {
				RowSet<Row> rows = ar.result();
				if (rows.size() > 0) {
					promise.complete(mapRowToBooking(rows.iterator().next()));
				} else {
					promise.complete(null);
				}
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	public Future<List<Booking>> getBookingsByPassengerId(Long passengerId) {
		Promise<List<Booking>> promise = Promise.promise();
		String sql = "SELECT * FROM bookings WHERE passenger_id = ? ORDER BY booking_date DESC";
		
		client.preparedQuery(sql).execute(Tuple.of(passengerId)).onComplete(ar -> {
			if (ar.succeeded()) {
				List<Booking> bookings = new ArrayList<>();
				RowSet<Row> rows = ar.result();
				for (Row row : rows) {
					bookings.add(mapRowToBooking(row));
				}
				promise.complete(bookings);
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	public Future<Booking> createBooking(String bookingReference, Long passengerId, Long flightId, 
										String seatNumber, String status, BigDecimal totalAmount) {
		Promise<Booking> promise = Promise.promise();
		String sql = "INSERT INTO bookings (booking_reference, passenger_id, flight_id, seat_number, status, total_amount) " +
					"VALUES (?, ?, ?, ?, ?, ?)";
		
		client.preparedQuery(sql)
			.execute(Tuple.of(bookingReference, passengerId, flightId, seatNumber, status, totalAmount))
			.onComplete(ar -> {
				if (ar.succeeded()) {
					// For H2, we can use a different approach to get the inserted ID
					// Let's query the last inserted booking by booking reference
					String selectSql = "SELECT * FROM bookings WHERE booking_reference = ? ORDER BY id DESC LIMIT 1";
					client.preparedQuery(selectSql)
						.execute(Tuple.of(bookingReference))
						.onComplete(selectAr -> {
							if (selectAr.succeeded()) {
								RowSet<Row> rows = selectAr.result();
								if (rows.iterator().hasNext()) {
									Booking booking = mapRowToBooking(rows.iterator().next());
									promise.complete(booking);
								} else {
									promise.fail("Failed to retrieve created booking");
								}
							} else {
								promise.fail(selectAr.cause());
							}
						});
				} else {
					promise.fail(ar.cause());
				}
			});
		
		return promise.future();
	}

	public Future<Boolean> cancelBooking(Long bookingId) {
		Promise<Boolean> promise = Promise.promise();
		String sql = "DELETE FROM bookings WHERE id = ?";
		
		client.preparedQuery(sql).execute(Tuple.of(bookingId)).onComplete(ar -> {
			if (ar.succeeded()) {
				int rowsAffected = ar.result().rowCount();
				promise.complete(rowsAffected > 0);
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	public Future<Boolean> updateFlightAvailableSeats(Long flightId, int newAvailableSeats) {
		Promise<Boolean> promise = Promise.promise();
		String sql = "UPDATE flights SET available_seats = ? WHERE id = ?";
		
		client.preparedQuery(sql).execute(Tuple.of(newAvailableSeats, flightId)).onComplete(ar -> {
			if (ar.succeeded()) {
				int rowsAffected = ar.result().rowCount();
				promise.complete(rowsAffected > 0);
			} else {
				promise.fail(ar.cause());
			}
		});
		
		return promise.future();
	}

	// Helper methods to map database rows to model objects
	private Airline mapRowToAirline(Row row) {
		Airline airline = new Airline();
		airline.setId(row.getLong("id"));
		airline.setCode(row.getString("code"));
		airline.setName(row.getString("name"));
		airline.setCountry(row.getString("country"));
		airline.setCreatedAt(row.getLocalDateTime("created_at"));
		airline.setUpdatedAt(row.getLocalDateTime("updated_at"));
		return airline;
	}

	private Flight mapRowToFlight(Row row) {
		Flight flight = new Flight();
		flight.setId(row.getLong("id"));
		flight.setFlightNumber(row.getString("flight_number"));
		flight.setAirlineId(row.getLong("airline_id"));
		flight.setDepartureAirport(row.getString("departure_airport"));
		flight.setArrivalAirport(row.getString("arrival_airport"));
		flight.setDepartureTime(row.getLocalDateTime("departure_time"));
		flight.setArrivalTime(row.getLocalDateTime("arrival_time"));
		flight.setAvailableSeats(row.getInteger("available_seats"));
		flight.setTotalSeats(row.getInteger("total_seats"));
		flight.setPrice(row.getBigDecimal("price"));
		flight.setStatus(row.getString("status"));
		flight.setCreatedAt(row.getLocalDateTime("created_at"));
		flight.setUpdatedAt(row.getLocalDateTime("updated_at"));
		return flight;
	}

	private Passenger mapRowToPassenger(Row row) {
		Passenger passenger = new Passenger();
		passenger.setId(row.getLong("id"));
		passenger.setFirstName(row.getString("first_name"));
		passenger.setLastName(row.getString("last_name"));
		passenger.setEmail(row.getString("email"));
		passenger.setPhone(row.getString("phone"));
		passenger.setPassportNumber(row.getString("passport_number"));
		passenger.setDateOfBirth(row.getLocalDate("date_of_birth"));
		passenger.setCreatedAt(row.getLocalDateTime("created_at"));
		passenger.setUpdatedAt(row.getLocalDateTime("updated_at"));
		return passenger;
	}

	private Booking mapRowToBooking(Row row) {
		Booking booking = new Booking();
		booking.setId(row.getLong("id"));
		booking.setBookingReference(row.getString("booking_reference"));
		booking.setPassengerId(row.getLong("passenger_id"));
		booking.setFlightId(row.getLong("flight_id"));
		booking.setBookingDate(row.getLocalDateTime("booking_date"));
		booking.setSeatNumber(row.getString("seat_number"));
		booking.setStatus(row.getString("status"));
		booking.setTotalAmount(row.getBigDecimal("total_amount"));
		booking.setCreatedAt(row.getLocalDateTime("created_at"));
		booking.setUpdatedAt(row.getLocalDateTime("updated_at"));
		return booking;
	}
}
