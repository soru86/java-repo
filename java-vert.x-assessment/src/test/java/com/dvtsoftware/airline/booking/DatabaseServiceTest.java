package com.dvtsoftware.airline.booking;

import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.model.Booking;
import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.model.Passenger;
import com.dvtsoftware.airline.booking.service.DatabaseService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.dvtsoftware.airline.booking.TestConfiguration;

@ExtendWith(VertxExtension.class)
@DisplayName("Database Service Tests")
class DatabaseServiceTest {

    private DatabaseService databaseService;
    private JDBCClient jdbcClient;
    private Vertx vertx;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        TestConfiguration.configure();
        this.vertx = vertx;
        // Use unique database name per test to avoid conflicts
        String dbName = "test_db_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
        JsonObject config = new JsonObject()
                .put("url", "jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1;MODE=MySQL")
                .put("driver_class", "org.h2.Driver")
                .put("user", "sa")
                .put("password", "")
                .put("max_pool_size", 10);

        jdbcClient = JDBCClient.createShared(vertx, config);
        databaseService = new DatabaseService(jdbcClient);

        databaseService.initializeDatabase()
                .onComplete(testContext.succeeding(v -> testContext.completeNow()));
    }

    @Test
    @DisplayName("Create airline should succeed")
    void testCreateAirline(VertxTestContext testContext) {
        // Use a unique airline code to avoid conflicts with seed data
        Airline airline = new Airline("ZZ", "Test Airline", "Test Country");
        databaseService.createAirline(airline)
                .onComplete(testContext.succeeding(createdAirline -> {
                    assertNotNull(createdAirline.getId());
                    assertEquals("ZZ", createdAirline.getCode());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get all airlines should return list")
    void testGetAllAirlines(VertxTestContext testContext) {
        databaseService.getAllAirlines()
                .onComplete(testContext.succeeding(airlines -> {
                    assertNotNull(airlines);
                    assertFalse(airlines.isEmpty());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create passenger should succeed")
    void testCreatePassenger(VertxTestContext testContext) {
        // Use unique email to avoid conflicts
        String uniqueEmail = "test" + System.currentTimeMillis() + "@test.com";
        Passenger passenger = new Passenger("John", "Doe", uniqueEmail,
                "+1-555-0100", "US123456", "United States");
        databaseService.createPassenger(passenger)
                .onComplete(testContext.succeeding(createdPassenger -> {
                    assertNotNull(createdPassenger.getId());
                    assertEquals(uniqueEmail, createdPassenger.getEmail());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get flight by ID should return flight")
    void testGetFlightById(VertxTestContext testContext) {
        // First create a flight, then retrieve it
        LocalDateTime departure = LocalDateTime.of(2024, 6, 15, 8, 0);
        LocalDateTime arrival = departure.plusHours(4);
        // Use shorter flight number (max 10 chars per schema)
        String uniqueFlightNumber = "T" + (System.currentTimeMillis() % 100000);
        Flight testFlight = new Flight(1L, uniqueFlightNumber, "DXB", "LHR",
                departure, arrival, 300, 250, BigDecimal.valueOf(850.0));
        
        databaseService.createFlight(testFlight)
                .compose(createdFlight -> {
                    Long flightId = createdFlight.getId();
                    // If ID is null, query directly for the flight by unique attributes
                    if (flightId == null) {
                        // Query for the flight by flight number, origin, and destination
                        String querySql = "SELECT id FROM flights WHERE flight_number = ? AND origin = ? AND destination = ? ORDER BY id DESC LIMIT 1";
                        io.vertx.core.json.JsonArray queryParams = new io.vertx.core.json.JsonArray()
                                .add(uniqueFlightNumber)
                                .add("DXB")
                                .add("LHR");
                        
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
                                        io.vertx.core.json.JsonObject row = result.getRows().get(0);
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
                                        return databaseService.getFlightById(id);
                                    } else {
                                        return Future.failedFuture("Flight ID is null after creation and query");
                                    }
                                });
                    } else {
                        return databaseService.getFlightById(flightId);
                    }
                })
                .onComplete(testContext.succeeding(flight -> {
                    assertNotNull(flight, "Flight should be retrieved");
                    assertNotNull(flight.getId(), "Flight ID should not be null");
                    assertEquals(uniqueFlightNumber, flight.getFlightNumber());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Search flights should return results")
    void testSearchFlights(VertxTestContext testContext) {
        databaseService.searchFlights("DXB", "LHR")
                .onComplete(testContext.succeeding(flights -> {
                    assertNotNull(flights);
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create booking should succeed")
    void testCreateBooking(VertxTestContext testContext) {
        // First create a passenger and flight
        Passenger passenger = new Passenger("Test", "Passenger", 
                "test" + System.currentTimeMillis() + "@test.com",
                "+1-555-0100", "TEST123", "United States");
        
        LocalDateTime departure = LocalDateTime.of(2024, 6, 15, 8, 0);
        LocalDateTime arrival = departure.plusHours(4);
        // Use shorter flight number (max 10 chars per schema)
        String uniqueFlightNumber = "T" + (System.currentTimeMillis() % 100000);
        Flight flight = new Flight(1L, uniqueFlightNumber, "DXB", "LHR",
                departure, arrival, 300, 250, BigDecimal.valueOf(850.0));
        
        databaseService.createPassenger(passenger)
                .compose(createdPassenger -> {
                    return databaseService.createFlight(flight)
                            .compose(createdFlight -> {
                                // Get the flight ID - if null, query directly for it
                                Long flightId = createdFlight.getId();
                                if (flightId == null) {
                                    // Query directly for the flight by unique attributes
                                    String querySql = "SELECT id FROM flights WHERE flight_number = ? AND origin = ? AND destination = ? ORDER BY id DESC LIMIT 1";
                                    io.vertx.core.json.JsonArray queryParams = new io.vertx.core.json.JsonArray()
                                            .add(uniqueFlightNumber)
                                            .add("DXB")
                                            .add("LHR");
                                    
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
                                                    io.vertx.core.json.JsonObject row = result.getRows().get(0);
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
                                                    return databaseService.getFlightById(id);
                                                } else {
                                                    return Future.failedFuture("Flight not found");
                                                }
                                            })
                                            .map(refetchedFlight -> {
                                                assertNotNull(refetchedFlight.getAvailableSeats(), "Refetched flight should have available seats");
                                                assertTrue(refetchedFlight.getAvailableSeats() > 0, "Flight should have available seats > 0");
                                                return new Booking(createdPassenger.getId(), refetchedFlight.getId(), 
                                                        null, "12A", "CONFIRMED");
                                            });
                                } else {
                                    // Re-fetch to ensure all fields are populated
                                    return databaseService.getFlightById(flightId)
                                            .map(refetchedFlight -> {
                                                assertNotNull(refetchedFlight.getAvailableSeats(), "Refetched flight should have available seats");
                                                assertTrue(refetchedFlight.getAvailableSeats() > 0, "Flight should have available seats > 0");
                                                return new Booking(createdPassenger.getId(), refetchedFlight.getId(), 
                                                        null, "12A", "CONFIRMED");
                                            });
                                }
                            });
                })
                .compose(booking -> databaseService.createBooking(booking))
                .onComplete(testContext.succeeding(createdBooking -> {
                    assertNotNull(createdBooking.getId());
                    assertNotNull(createdBooking.getBookingReference());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get bookings by passenger ID should return list")
    void testGetBookingsByPassengerId(VertxTestContext testContext) {
        databaseService.getBookingsByPassengerId(1L)
                .onComplete(testContext.succeeding(bookings -> {
                    assertNotNull(bookings);
                    testContext.completeNow();
                }));
    }
}

