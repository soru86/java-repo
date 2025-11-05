package com.dvtsoftware.airline.booking;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import com.dvtsoftware.airline.booking.TestConfiguration;

@ExtendWith(VertxExtension.class)
@DisplayName("Main Verticle Integration Tests")
class MainVerticleIntegrationTest {

    private WebClient webClient;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        TestConfiguration.configure();
        // Use unique database name per test to avoid conflicts with seed data
        String dbName = "test_db_" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
        // Use unique port per test to avoid binding conflicts
        int testPort = 8080 + (int)(System.currentTimeMillis() % 50000);
        JsonObject config = new JsonObject()
                .put("server", new JsonObject()
                        .put("port", testPort)
                        .put("host", "127.0.0.1"))
                .put("database", new JsonObject()
                        .put("url", "jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1;MODE=MySQL")
                        .put("driver_class", "org.h2.Driver")
                        .put("user", "sa")
                        .put("password", "")
                        .put("max_pool_size", 10));

        webClient = WebClient.create(vertx, new WebClientOptions().setDefaultPort(testPort));

        vertx.deployVerticle(new MainVerticle(), 
                new io.vertx.core.DeploymentOptions().setConfig(config))
                .compose(id -> {
                    // Wait a bit for the server to be fully ready and database initialized
                    return Future.<Void>future(promise -> {
                        vertx.setTimer(100, ignored -> promise.complete());
                    });
                })
                .onSuccess(v -> testContext.completeNow())
                .onFailure(testContext::failNow);
    }

    @Test
    @DisplayName("Health check endpoint should return UP")
    void testHealthCheck(Vertx vertx, VertxTestContext testContext) {
        webClient.get("/health")
                .send()
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode());
                    JsonObject body = response.bodyAsJsonObject();
                    assertEquals("UP", body.getString("status"));
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get all airlines should return list")
    void testGetAllAirlines(Vertx vertx, VertxTestContext testContext) {
        webClient.get("/airlines")
                .send()
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode());
                    JsonObject body = response.bodyAsJsonObject();
                    assertNotNull(body.getJsonArray("airlines"));
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create airline should return 201")
    void testCreateAirline(Vertx vertx, VertxTestContext testContext) {
        JsonObject airline = new JsonObject()
                .put("code", "VS")
                .put("name", "Virgin Atlantic")
                .put("country", "United Kingdom");

        webClient.post("/airlines")
                .sendJsonObject(airline)
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(201, response.statusCode());
                    JsonObject body = response.bodyAsJsonObject();
                    assertNotNull(body.getLong("id"));
                    assertEquals("VS", body.getString("code"));
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Search flights should return results")
    void testSearchFlights(Vertx vertx, VertxTestContext testContext) {
        webClient.get("/flights/search")
                .addQueryParam("from", "DXB")
                .addQueryParam("to", "LHR")
                .send()
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode(), "Status code should be 200. Response: " + response.bodyAsString());
                    JsonObject body = response.bodyAsJsonObject();
                    assertNotNull(body.getJsonArray("flights"), "Flights array should not be null");
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get flight by ID should return flight")
    void testGetFlightById(Vertx vertx, VertxTestContext testContext) {
        // First search for a flight to get a valid ID
        webClient.get("/flights/search")
                .addQueryParam("from", "DXB")
                .addQueryParam("to", "LHR")
                .send()
                .compose(response -> {
                    assertEquals(200, response.statusCode(), "Search should succeed");
                    JsonObject body = response.bodyAsJsonObject();
                    var flights = body.getJsonArray("flights");
                    assertNotNull(flights, "Flights should not be null");
                    assertFalse(flights.isEmpty(), "Should have at least one flight");
                    
                    // Get the first flight's ID
                    JsonObject flight = flights.getJsonObject(0);
                    Object idObj = flight.getValue("id");
                    Long flightId = null;
                    if (idObj instanceof Number) {
                        flightId = ((Number) idObj).longValue();
                    } else if (idObj != null) {
                        flightId = flight.getLong("id");
                    }
                    assertNotNull(flightId, "Flight ID should not be null from search");
                    
                    // Now get the flight by ID
                    return webClient.get("/flights/" + flightId).send();
                })
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode(), "Status code should be 200. Response: " + response.bodyAsString());
                    JsonObject body = response.bodyAsJsonObject();
                    Object idObj = body.getValue("id");
                    assertNotNull(idObj, "Flight ID should not be null");
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create passenger should return 201")
    void testCreatePassenger(Vertx vertx, VertxTestContext testContext) {
        JsonObject passenger = new JsonObject()
                .put("firstName", "John")
                .put("lastName", "Doe")
                .put("email", "john.doe@example.com")
                .put("phone", "+1-555-0100")
                .put("passportNumber", "US123456")
                .put("nationality", "United States");

        webClient.post("/passengers")
                .sendJsonObject(passenger)
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(201, response.statusCode());
                    JsonObject body = response.bodyAsJsonObject();
                    assertNotNull(body.getLong("id"));
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create booking should return 201")
    void testCreateBooking(Vertx vertx, VertxTestContext testContext) {
        // First find a flight with available seats, then create a booking
        // Use JFK to DOH route which has flight QR702 with 340 available seats (less likely to be fully booked)
        webClient.get("/flights/search")
                .addQueryParam("from", "JFK")
                .addQueryParam("to", "DOH")
                .send()
                .compose(response -> {
                    assertEquals(200, response.statusCode(), "Search flights should succeed. Response: " + response.bodyAsString());
                    JsonObject body = response.bodyAsJsonObject();
                    var flights = body.getJsonArray("flights");
                    assertNotNull(flights, "Flights array should not be null");
                    assertFalse(flights.isEmpty(), "Should have at least one flight with available seats");
                    
                    // Get the first flight with available seats
                    JsonObject flight = flights.getJsonObject(0);
                    Object idObj = flight.getValue("id");
                    Long flightId = null;
                    if (idObj instanceof Number) {
                        flightId = ((Number) idObj).longValue();
                    } else if (idObj != null) {
                        flightId = flight.getLong("id");
                    }
                    assertNotNull(flightId, "Flight ID should not be null");
                    
                    // Verify the flight has available seats
                    Object availableSeatsObj = flight.getValue("availableSeats");
                    Integer availableSeats = null;
                    if (availableSeatsObj instanceof Number) {
                        availableSeats = ((Number) availableSeatsObj).intValue();
                    } else if (availableSeatsObj != null) {
                        availableSeats = flight.getInteger("availableSeats");
                    }
                    assertNotNull(availableSeats, "Available seats should not be null");
                    assertTrue(availableSeats > 0, "Flight should have available seats > 0, but has: " + availableSeats);
                    
                    // Create booking with passenger 1 (exists in seed data)
                    JsonObject booking = new JsonObject()
                            .put("passengerId", 1)
                            .put("flightId", flightId)
                            .put("seatNumber", "15B");
                    
                    return webClient.post("/bookings").sendJsonObject(booking);
                })
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(201, response.statusCode(), "Status code should be 201. Response: " + response.bodyAsString());
                    JsonObject body = response.bodyAsJsonObject();
                    Object idObj = body.getValue("id");
                    assertNotNull(idObj, "Booking ID should not be null");
                    assertNotNull(body.getString("bookingReference"), "Booking reference should not be null");
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get booking by ID should return booking")
    void testGetBookingById(Vertx vertx, VertxTestContext testContext) {
        // First get bookings for passenger 1 to find a valid booking ID
        webClient.get("/passengers/1/bookings")
                .send()
                .compose(response -> {
                    assertEquals(200, response.statusCode(), "Get bookings should succeed");
                    JsonObject body = response.bodyAsJsonObject();
                    var bookings = body.getJsonArray("bookings");
                    assertNotNull(bookings, "Bookings should not be null");
                    assertFalse(bookings.isEmpty(), "Should have at least one booking");
                    
                    // Get the first booking's ID
                    JsonObject booking = bookings.getJsonObject(0);
                    Object idObj = booking.getValue("id");
                    Long bookingId = null;
                    if (idObj instanceof Number) {
                        bookingId = ((Number) idObj).longValue();
                    } else if (idObj != null) {
                        bookingId = booking.getLong("id");
                    }
                    assertNotNull(bookingId, "Booking ID should not be null from list");
                    
                    // Now get the booking by ID
                    return webClient.get("/bookings/" + bookingId).send();
                })
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode(), "Status code should be 200. Response: " + response.bodyAsString());
                    JsonObject body = response.bodyAsJsonObject();
                    Object idObj = body.getValue("id");
                    assertNotNull(idObj, "Booking ID should not be null");
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get bookings by passenger ID should return list")
    void testGetBookingsByPassengerId(Vertx vertx, VertxTestContext testContext) {
        webClient.get("/passengers/1/bookings")
                .send()
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode());
                    JsonObject body = response.bodyAsJsonObject();
                    assertNotNull(body.getJsonArray("bookings"));
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create airline with missing fields should return 400")
    void testCreateAirlineMissingFields(Vertx vertx, VertxTestContext testContext) {
        JsonObject airline = new JsonObject()
                .put("code", "VS");

        webClient.post("/airlines")
                .sendJsonObject(airline)
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Get flight with non-existent ID should return 404")
    void testGetFlightByIdNotFound(Vertx vertx, VertxTestContext testContext) {
        webClient.get("/flights/99999")
                .send()
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(404, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Search flights with missing parameters should return 400")
    void testSearchFlightsMissingParams(Vertx vertx, VertxTestContext testContext) {
        webClient.get("/flights/search")
                .send()
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(400, response.statusCode());
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Create booking with invalid flight should fail")
    void testCreateBookingInvalidFlight(Vertx vertx, VertxTestContext testContext) {
        JsonObject booking = new JsonObject()
                .put("passengerId", 1)
                .put("flightId", 99999)
                .put("seatNumber", "12A");

        webClient.post("/bookings")
                .sendJsonObject(booking)
                .onComplete(testContext.succeeding(response -> {
                    assertTrue(response.statusCode() >= 400);
                    testContext.completeNow();
                }));
    }

    @Test
    @DisplayName("Cancel booking should succeed")
    void testCancelBooking(Vertx vertx, VertxTestContext testContext) {
        // First find a flight with available seats, create a booking, then cancel it
        // Use SIN to SFO route which has flight UA601 with 305 available seats
        webClient.get("/flights/search")
                .addQueryParam("from", "SIN")
                .addQueryParam("to", "SFO")
                .send()
                .compose(response -> {
                    assertEquals(200, response.statusCode(), "Search flights should succeed. Response: " + response.bodyAsString());
                    JsonObject body = response.bodyAsJsonObject();
                    var flights = body.getJsonArray("flights");
                    assertNotNull(flights, "Flights array should not be null");
                    assertFalse(flights.isEmpty(), "Should have at least one flight with available seats");
                    
                    // Get the first flight with available seats
                    JsonObject flight = flights.getJsonObject(0);
                    Object idObj = flight.getValue("id");
                    Long flightId = null;
                    if (idObj instanceof Number) {
                        flightId = ((Number) idObj).longValue();
                    } else if (idObj != null) {
                        flightId = flight.getLong("id");
                    }
                    assertNotNull(flightId, "Flight ID should not be null");
                    
                    // Verify the flight has available seats
                    Object availableSeatsObj = flight.getValue("availableSeats");
                    Integer availableSeats = null;
                    if (availableSeatsObj instanceof Number) {
                        availableSeats = ((Number) availableSeatsObj).intValue();
                    } else if (availableSeatsObj != null) {
                        availableSeats = flight.getInteger("availableSeats");
                    }
                    assertNotNull(availableSeats, "Available seats should not be null");
                    assertTrue(availableSeats > 0, "Flight should have available seats > 0, but has: " + availableSeats);
                    
                    // Create booking with passenger 1 (exists in seed data)
                    JsonObject booking = new JsonObject()
                            .put("passengerId", 1)
                            .put("flightId", flightId)
                            .put("seatNumber", "20C");
                    
                    return webClient.post("/bookings").sendJsonObject(booking);
                })
                .compose(response -> {
                    assertEquals(201, response.statusCode(), "Create booking should succeed. Response: " + response.bodyAsString());
                    JsonObject bookingBody = response.bodyAsJsonObject();
                    Object idObj = bookingBody.getValue("id");
                    Long bookingId = null;
                    if (idObj instanceof Number) {
                        bookingId = ((Number) idObj).longValue();
                    } else if (idObj != null) {
                        bookingId = bookingBody.getLong("id");
                    }
                    assertNotNull(bookingId, "Booking ID should not be null");
                    // Cancel the booking
                    return webClient.delete("/bookings/" + bookingId).send();
                })
                .onComplete(testContext.succeeding(response -> {
                    assertEquals(200, response.statusCode(), "Cancel booking should succeed. Response: " + response.bodyAsString());
                    testContext.completeNow();
                }));
    }
}

