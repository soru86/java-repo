package com.dvtsoftware.airline.booking.service;

import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.model.Booking;
import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.model.Passenger;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Reactive database service for airline booking operations.
 * All methods are non-blocking and return Futures.
 */
public class DatabaseService implements IDatabaseService {
    private final JDBCClient jdbcClient;
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DatabaseService(JDBCClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Helper method to get a connection and wrap it in a Future
     */
    private Future<SQLConnection> getConnection() {
        return Future.future(promise -> {
            jdbcClient.getConnection(ar -> {
                if (ar.succeeded()) {
                    promise.complete(ar.result());
                } else {
                    promise.fail(ar.cause());
                }
            });
        });
    }

    /**
     * Initialize database schema and seed data.
     */
    public Future<Void> initializeDatabase() {
        return Future.future(promise -> {
            jdbcClient.getConnection(ar -> {
                if (ar.failed()) {
                    promise.fail(ar.cause());
                    return;
                }
                SQLConnection conn = ar.result();
                loadAndExecuteSQLFile(conn, "schema.sql")
                        .compose(v -> loadAndExecuteSQLFile(conn, "data.sql"))
                        .onComplete(result -> {
                            conn.close();
                            if (result.failed()) {
                                promise.fail(result.cause());
                            } else {
                                promise.complete();
                            }
                        });
            });
        });
    }

    private Future<Void> loadAndExecuteSQLFile(SQLConnection conn, String filename) {
        return Future.future(promise -> {
            try (var inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
                if (inputStream == null) {
                    promise.fail("SQL file not found: " + filename);
                    return;
                }
                String sql = new String(inputStream.readAllBytes());
                String[] statements = sql.split(";");
                executeStatements(conn, statements, 0, promise);
            } catch (Exception e) {
                promise.fail(e);
            }
        });
    }

    private void executeStatements(SQLConnection conn, String[] statements, int index, io.vertx.core.Promise<Void> promise) {
        if (index >= statements.length) {
            promise.complete();
            return;
        }

        String statement = statements[index].trim();
        if (statement.isEmpty()) {
            executeStatements(conn, statements, index + 1, promise);
            return;
        }

        conn.execute(statement, ar -> {
            if (ar.failed()) {
                promise.fail(ar.cause());
            } else {
                executeStatements(conn, statements, index + 1, promise);
            }
        });
    }

    // Airline Operations
    public Future<Airline> createAirline(Airline airline) {
        String sql = "INSERT INTO airlines (code, name, country) VALUES (?, ?, ?)";
        JsonArray params = new JsonArray().add(airline.getCode()).add(airline.getName()).add(airline.getCountry());

        return getConnection()
                .compose(conn -> {
                    return Future.<io.vertx.ext.sql.UpdateResult>future(promise -> {
                        conn.updateWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(updateResult -> {
                        airline.setId(updateResult.getKeys().getLong(0));
                        conn.close();
                        return airline;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    public Future<List<Airline>> getAllAirlines() {
        String sql = "SELECT id, code, name, country, created_at FROM airlines ORDER BY name";

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.query(sql, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        List<Airline> airlines = mapToAirlines(result);
                        conn.close();
                        return airlines;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    public Future<Airline> getAirlineById(Long id) {
        String sql = "SELECT id, code, name, country, created_at FROM airlines WHERE id = ?";
        JsonArray params = new JsonArray().add(id);

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.queryWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        Airline airline = result.getRows().isEmpty() ? null : mapToAirline(result.getRows().get(0));
                        conn.close();
                        return airline;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    // Flight Operations
    public Future<Flight> createFlight(Flight flight) {
        String sql = "INSERT INTO flights (airline_id, flight_number, origin, destination, " +
                "departure_time, arrival_time, total_seats, available_seats, price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Convert LocalDateTime to Timestamp for H2 compatibility
        java.sql.Timestamp departureTimestamp = java.sql.Timestamp.valueOf(flight.getDepartureTime());
        java.sql.Timestamp arrivalTimestamp = java.sql.Timestamp.valueOf(flight.getArrivalTime());
        
        JsonArray params = new JsonArray()
                .add(flight.getAirlineId())
                .add(flight.getFlightNumber())
                .add(flight.getOrigin())
                .add(flight.getDestination())
                .add(departureTimestamp)
                .add(arrivalTimestamp)
                .add(flight.getTotalSeats())
                .add(flight.getAvailableSeats())
                .add(flight.getPrice().doubleValue());

        return getConnection()
                .compose(conn -> {
                    return Future.<io.vertx.ext.sql.UpdateResult>future(promise -> {
                        conn.updateWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .compose(updateResult -> {
                        // Try to get generated key - H2 may return it in different formats
                        Long generatedId = null;
                        try {
                            if (updateResult.getKeys() != null && !updateResult.getKeys().isEmpty()) {
                                try {
                                    generatedId = updateResult.getKeys().getLong(0);
                                } catch (Exception e) {
                                    // If getLong fails, try alternative approach
                                    Object idObj = updateResult.getKeys().getValue(0);
                                    if (idObj instanceof Number) {
                                        generatedId = ((Number) idObj).longValue();
                                    } else if (idObj instanceof JsonObject) {
                                        generatedId = ((JsonObject) idObj).getLong("id");
                                    } else if (idObj instanceof String) {
                                        try {
                                            generatedId = Long.parseLong((String) idObj);
                                        } catch (NumberFormatException nfe) {
                                            // Ignore
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // ID will remain null - will need to query for it
                        }
                        
                        if (generatedId != null) {
                            flight.setId(generatedId);
                            conn.close();
                            return Future.succeededFuture(flight);
                        } else {
                            // Fallback: Query for the flight by flight number to get its ID
                            String querySql = "SELECT id FROM flights WHERE flight_number = ? AND origin = ? AND destination = ? ORDER BY id DESC LIMIT 1";
                            JsonArray queryParams = new JsonArray()
                                    .add(flight.getFlightNumber())
                                    .add(flight.getOrigin())
                                    .add(flight.getDestination());
                            
                            return Future.<io.vertx.ext.sql.ResultSet>future(promise -> {
                                conn.queryWithParams(querySql, queryParams, ar -> {
                                    if (ar.succeeded()) {
                                        promise.complete(ar.result());
                                    } else {
                                        promise.fail(ar.cause());
                                    }
                                });
                            })
                            .map(result -> {
                                if (!result.getRows().isEmpty()) {
                                    JsonObject row = result.getRows().get(0);
                                    Object idObj = row.getValue("id");
                                    if (idObj instanceof Number) {
                                        flight.setId(((Number) idObj).longValue());
                                    } else {
                                        flight.setId(row.getLong("id"));
                                    }
                                }
                                conn.close();
                                return flight;
                            })
                            .recover(error -> {
                                conn.close();
                                // Return flight even without ID - caller can handle it
                                return Future.succeededFuture(flight);
                            });
                        }
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    public Future<Flight> getFlightById(Long id) {
        String sql = "SELECT id, airline_id, flight_number, origin, destination, " +
                "departure_time, arrival_time, total_seats, available_seats, price, created_at " +
                "FROM flights WHERE id = ?";
        JsonArray params = new JsonArray().add(id);

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.queryWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        Flight flight = result.getRows().isEmpty() ? null : mapToFlight(result.getRows().get(0));
                        conn.close();
                        return flight;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    public Future<List<Flight>> searchFlights(String from, String to) {
        String sql = "SELECT id, airline_id, flight_number, origin, destination, " +
                "departure_time, arrival_time, total_seats, available_seats, price, created_at " +
                "FROM flights WHERE origin = ? AND destination = ? AND available_seats > 0 " +
                "ORDER BY departure_time";
        JsonArray params = new JsonArray().add(from).add(to);

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.queryWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        List<Flight> flights = mapToFlights(result);
                        conn.close();
                        return flights;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    // Passenger Operations
    public Future<Passenger> createPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (first_name, last_name, email, phone, passport_number, nationality) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        JsonArray params = new JsonArray()
                .add(passenger.getFirstName())
                .add(passenger.getLastName())
                .add(passenger.getEmail())
                .add(passenger.getPhone())
                .add(passenger.getPassportNumber())
                .add(passenger.getNationality());

        return getConnection()
                .compose(conn -> {
                    return Future.<io.vertx.ext.sql.UpdateResult>future(promise -> {
                        conn.updateWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(updateResult -> {
                        passenger.setId(updateResult.getKeys().getLong(0));
                        conn.close();
                        return passenger;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    public Future<Passenger> getPassengerById(Long id) {
        String sql = "SELECT id, first_name, last_name, email, phone, passport_number, nationality, created_at " +
                "FROM passengers WHERE id = ?";
        JsonArray params = new JsonArray().add(id);

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.queryWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        Passenger passenger = result.getRows().isEmpty() ? null : mapToPassenger(result.getRows().get(0));
                        conn.close();
                        return passenger;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    // Booking Operations
    public Future<Booking> createBooking(Booking booking) {
        // Generate unique booking reference
        final String bookingRef = UUID.randomUUID().toString().substring(0, 13).toUpperCase().replace("-", "");

        // First check if flight has available seats
        return getFlightById(booking.getFlightId())
                .compose(flight -> {
                    if (flight == null) {
                        return Future.failedFuture("Flight not found");
                    }
                    Integer availableSeats = flight.getAvailableSeats();
                    if (availableSeats == null || availableSeats <= 0) {
                        return Future.failedFuture("No available seats on this flight");
                    }

                    // Create booking
                    String sql = "INSERT INTO bookings (passenger_id, flight_id, booking_reference, seat_number, booking_status) " +
                            "VALUES (?, ?, ?, ?, ?)";
                    JsonArray params = new JsonArray()
                            .add(booking.getPassengerId())
                            .add(booking.getFlightId())
                            .add(bookingRef)
                            .add(booking.getSeatNumber())
                            .add("CONFIRMED");

                    return getConnection()
                            .compose(conn -> {
                                return Future.<io.vertx.ext.sql.UpdateResult>future(promise -> {
                                    conn.updateWithParams(sql, params, ar -> {
                                        if (ar.succeeded()) {
                                            promise.complete(ar.result());
                                        } else {
                                            promise.fail(ar.cause());
                                        }
                                    });
                                })
                                .compose(updateResult -> {
                                    // Try to get generated key - H2 may return it in different formats
                                    Long bookingId = null;
                                    try {
                                        if (updateResult.getKeys() != null && !updateResult.getKeys().isEmpty()) {
                                            try {
                                                bookingId = updateResult.getKeys().getLong(0);
                                            } catch (Exception e) {
                                                // If getLong fails, try alternative approach
                                                Object idObj = updateResult.getKeys().getValue(0);
                                                if (idObj instanceof Number) {
                                                    bookingId = ((Number) idObj).longValue();
                                                } else if (idObj instanceof String) {
                                                    try {
                                                        bookingId = Long.parseLong((String) idObj);
                                                    } catch (NumberFormatException nfe) {
                                                        // Ignore
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        // ID will remain null - will need to query for it
                                    }
                                    
                                    final Long finalBookingId = bookingId;
                                    // Decrease available seats
                                    return updateFlightSeats(conn, booking.getFlightId(), -1)
                                            .map(v -> finalBookingId);
                                })
                                .compose(bookingId -> {
                                    conn.close();
                                    if (bookingId != null) {
                                        return getBookingById(bookingId);
                                    } else {
                                        // Fallback: Query for the booking by booking reference
                                        String querySql = "SELECT id FROM bookings WHERE booking_reference = ? ORDER BY id DESC LIMIT 1";
                                        JsonArray queryParams = new JsonArray().add(bookingRef);
                                        
                                        return Future.<io.vertx.ext.sql.ResultSet>future(promise -> {
                                            jdbcClient.queryWithParams(querySql, queryParams, ar -> {
                                                if (ar.succeeded()) {
                                                    promise.complete(ar.result());
                                                } else {
                                                    promise.fail(ar.cause());
                                                }
                                            });
                                        })
                                        .map(result -> {
                                            if (!result.getRows().isEmpty()) {
                                                JsonObject row = result.getRows().get(0);
                                                Object idObj = row.getValue("id");
                                                if (idObj instanceof Number) {
                                                    return ((Number) idObj).longValue();
                                                }
                                                return row.getLong("id");
                                            }
                                            return null;
                                        })
                                        .compose(id -> {
                                            if (id != null) {
                                                return getBookingById(id);
                                            } else {
                                                return Future.failedFuture("Booking ID is null after creation and query");
                                            }
                                        });
                                    }
                                })
                                .recover(error -> {
                                    conn.close();
                                    return Future.failedFuture(error);
                                });
                            });
                });
    }

    private Future<Void> updateFlightSeats(SQLConnection conn, Long flightId, int delta) {
        String sql = "UPDATE flights SET available_seats = available_seats + ? WHERE id = ?";
        JsonArray params = new JsonArray().add(delta).add(flightId);
        return Future.future(promise -> {
            conn.updateWithParams(sql, params, ar -> {
                if (ar.succeeded()) {
                    promise.complete();
                } else {
                    promise.fail(ar.cause());
                }
            });
        });
    }

    public Future<Booking> getBookingById(Long id) {
        String sql = "SELECT id, passenger_id, flight_id, booking_reference, seat_number, " +
                "booking_status, booking_date, created_at FROM bookings WHERE id = ?";
        JsonArray params = new JsonArray().add(id);

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.queryWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        Booking booking = result.getRows().isEmpty() ? null : mapToBooking(result.getRows().get(0));
                        conn.close();
                        return booking;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    public Future<Void> cancelBooking(Long id) {
        // Get booking first
        return getBookingById(id)
                .compose(booking -> {
                    if (booking == null) {
                        return Future.<Void>failedFuture("Booking not found");
                    }
                    if ("CANCELLED".equals(booking.getBookingStatus())) {
                        return Future.<Void>failedFuture("Booking is already cancelled");
                    }

                    String sql = "UPDATE bookings SET booking_status = 'CANCELLED' WHERE id = ?";
                    JsonArray params = new JsonArray().add(id);
                    final Long flightId = booking.getFlightId();

                    return getConnection()
                            .compose(conn -> {
                                return Future.<io.vertx.ext.sql.UpdateResult>future(promise -> {
                                    conn.updateWithParams(sql, params, ar -> {
                                        if (ar.succeeded()) {
                                            promise.complete(ar.result());
                                        } else {
                                            promise.fail(ar.cause());
                                        }
                                    });
                                })
                                .compose(updateResult -> {
                                    // Increase available seats
                                    return updateFlightSeats(conn, flightId, 1);
                                })
                                .compose(v -> {
                                    conn.close();
                                    return Future.<Void>succeededFuture();
                                })
                                .recover(error -> {
                                    conn.close();
                                    return Future.<Void>failedFuture(error);
                                });
                            });
                });
    }

    public Future<List<Booking>> getBookingsByPassengerId(Long passengerId) {
        String sql = "SELECT id, passenger_id, flight_id, booking_reference, seat_number, " +
                "booking_status, booking_date, created_at FROM bookings WHERE passenger_id = ? " +
                "ORDER BY booking_date DESC";
        JsonArray params = new JsonArray().add(passengerId);

        return getConnection()
                .compose(conn -> {
                    return Future.<ResultSet>future(promise -> {
                        conn.queryWithParams(sql, params, ar -> {
                            if (ar.succeeded()) {
                                promise.complete(ar.result());
                            } else {
                                promise.fail(ar.cause());
                            }
                        });
                    })
                    .map(result -> {
                        List<Booking> bookings = mapToBookings(result);
                        conn.close();
                        return bookings;
                    })
                    .recover(error -> {
                        conn.close();
                        return Future.failedFuture(error);
                    });
                });
    }

    // Mapping methods
    private List<Airline> mapToAirlines(ResultSet resultSet) {
        return resultSet.getRows().stream()
                .map(this::mapToAirline)
                .collect(Collectors.toList());
    }

    private Airline mapToAirline(JsonObject row) {
        Airline airline = new Airline();
        airline.setId(row.getLong("id"));
        airline.setCode(row.getString("code"));
        airline.setName(row.getString("name"));
        airline.setCountry(row.getString("country"));
        if (row.getValue("created_at") != null) {
            airline.setCreatedAt(LocalDateTime.parse(row.getString("created_at"), DATETIME_FORMATTER));
        }
        return airline;
    }

    private List<Flight> mapToFlights(ResultSet resultSet) {
        return resultSet.getRows().stream()
                .map(this::mapToFlight)
                .collect(Collectors.toList());
    }

    private Flight mapToFlight(JsonObject row) {
        Flight flight = new Flight();
        // Handle ID - H2 might return it as INTEGER, BIGINT, or in different case
        Object idObj = row.getValue("id");
        if (idObj != null) {
            if (idObj instanceof Number) {
                flight.setId(((Number) idObj).longValue());
            } else if (idObj instanceof String) {
                try {
                    flight.setId(Long.parseLong((String) idObj));
                } catch (NumberFormatException e) {
                    // Ignore
                }
            } else {
                flight.setId(row.getLong("id"));
            }
        } else {
            // Try uppercase ID
            idObj = row.getValue("ID");
            if (idObj instanceof Number) {
                flight.setId(((Number) idObj).longValue());
            }
        }
        flight.setAirlineId(row.getLong("airline_id"));
        // Handle flight_number - might be in different case
        String flightNumber = row.getString("flight_number");
        if (flightNumber == null) {
            flightNumber = row.getString("FLIGHT_NUMBER");
        }
        flight.setFlightNumber(flightNumber);
        flight.setOrigin(row.getString("origin"));
        flight.setDestination(row.getString("destination"));
        if (row.getValue("departure_time") != null) {
            Object depTime = row.getValue("departure_time");
            if (depTime instanceof java.sql.Timestamp) {
                flight.setDepartureTime(((java.sql.Timestamp) depTime).toLocalDateTime());
            } else if (depTime instanceof String) {
                flight.setDepartureTime(LocalDateTime.parse((String) depTime, DATETIME_FORMATTER));
            }
        }
        if (row.getValue("arrival_time") != null) {
            Object arrTime = row.getValue("arrival_time");
            if (arrTime instanceof java.sql.Timestamp) {
                flight.setArrivalTime(((java.sql.Timestamp) arrTime).toLocalDateTime());
            } else if (arrTime instanceof String) {
                flight.setArrivalTime(LocalDateTime.parse((String) arrTime, DATETIME_FORMATTER));
            }
        }
        flight.setTotalSeats(row.getInteger("total_seats"));
        // Handle available_seats - it should never be null from database, but handle it safely
        Object availableSeatsObj = row.getValue("available_seats");
        if (availableSeatsObj != null) {
            if (availableSeatsObj instanceof Number) {
                flight.setAvailableSeats(((Number) availableSeatsObj).intValue());
            } else if (availableSeatsObj instanceof String) {
                try {
                    flight.setAvailableSeats(Integer.parseInt((String) availableSeatsObj));
                } catch (NumberFormatException e) {
                    // If parsing fails, try getInteger
                    flight.setAvailableSeats(row.getInteger("available_seats"));
                }
            } else {
                flight.setAvailableSeats(row.getInteger("available_seats"));
            }
        } else {
            // Try uppercase column name
            availableSeatsObj = row.getValue("AVAILABLE_SEATS");
            if (availableSeatsObj instanceof Number) {
                flight.setAvailableSeats(((Number) availableSeatsObj).intValue());
            } else {
                // If available_seats is null, default to total_seats or 0
                Integer totalSeats = flight.getTotalSeats();
                flight.setAvailableSeats(totalSeats != null ? totalSeats : 0);
            }
        }
        if (row.getValue("price") != null) {
            flight.setPrice(BigDecimal.valueOf(row.getDouble("price")));
        }
        if (row.getValue("created_at") != null) {
            Object createdAtObj = row.getValue("created_at");
            if (createdAtObj instanceof java.sql.Timestamp) {
                flight.setCreatedAt(((java.sql.Timestamp) createdAtObj).toLocalDateTime());
            } else if (createdAtObj instanceof String) {
                flight.setCreatedAt(LocalDateTime.parse((String) createdAtObj, DATETIME_FORMATTER));
            }
        }
        return flight;
    }

    private Passenger mapToPassenger(JsonObject row) {
        Passenger passenger = new Passenger();
        passenger.setId(row.getLong("id"));
        passenger.setFirstName(row.getString("first_name"));
        passenger.setLastName(row.getString("last_name"));
        passenger.setEmail(row.getString("email"));
        passenger.setPhone(row.getString("phone"));
        passenger.setPassportNumber(row.getString("passport_number"));
        passenger.setNationality(row.getString("nationality"));
        if (row.getValue("created_at") != null) {
            passenger.setCreatedAt(LocalDateTime.parse(row.getString("created_at"), DATETIME_FORMATTER));
        }
        return passenger;
    }

    private List<Booking> mapToBookings(ResultSet resultSet) {
        return resultSet.getRows().stream()
                .map(this::mapToBooking)
                .collect(Collectors.toList());
    }

    private Booking mapToBooking(JsonObject row) {
        Booking booking = new Booking();
        // Handle ID - H2 might return it as INTEGER, BIGINT, or in different case
        Object idObj = row.getValue("id");
        if (idObj != null) {
            if (idObj instanceof Number) {
                booking.setId(((Number) idObj).longValue());
            } else if (idObj instanceof String) {
                try {
                    booking.setId(Long.parseLong((String) idObj));
                } catch (NumberFormatException e) {
                    // Ignore
                }
            } else {
                booking.setId(row.getLong("id"));
            }
        } else {
            // Try uppercase ID
            idObj = row.getValue("ID");
            if (idObj instanceof Number) {
                booking.setId(((Number) idObj).longValue());
            }
        }
        booking.setPassengerId(row.getLong("passenger_id"));
        booking.setFlightId(row.getLong("flight_id"));
        // Handle booking_reference - might be in different case
        String bookingRef = row.getString("booking_reference");
        if (bookingRef == null) {
            bookingRef = row.getString("BOOKING_REFERENCE");
        }
        booking.setBookingReference(bookingRef);
        booking.setSeatNumber(row.getString("seat_number"));
        booking.setBookingStatus(row.getString("booking_status"));
        if (row.getValue("booking_date") != null) {
            booking.setBookingDate(LocalDateTime.parse(row.getString("booking_date"), DATETIME_FORMATTER));
        }
        if (row.getValue("created_at") != null) {
            booking.setCreatedAt(LocalDateTime.parse(row.getString("created_at"), DATETIME_FORMATTER));
        }
        return booking;
    }
}
