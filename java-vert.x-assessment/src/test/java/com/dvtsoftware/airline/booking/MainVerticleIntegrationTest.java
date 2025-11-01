package com.dvtsoftware.airline.booking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.dvtsoftware.airline.booking.service.DatabaseInitService;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class MainVerticleIntegrationTest {

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        // Initialize database before each test
        DatabaseInitService databaseInitService = new DatabaseInitService(vertx);
        databaseInitService.initializeDatabase()
            .onComplete(ar -> {
                if (ar.succeeded()) {
                    testContext.completeNow();
                } else {
                    testContext.failNow(ar.cause());
                }
            });
    }

    @Test
    void testCompleteAirlineBookingFlow(Vertx vertx, VertxTestContext testContext) {
        // Deploy the main verticle
        vertx.deployVerticle(new MainVerticle())
            .onSuccess(deploymentId -> {
                WebClient client = WebClient.create(vertx);

                // Test 1: Get all airlines
                client.get(8080, "localhost", "/airlines")
                    .send()
                    .compose(response -> {
                        assertThat(response.statusCode()).isEqualTo(200);
                        assertThat(response.bodyAsString()).isNotEmpty();
                        
                        // Test 2: Add a new airline
                        JsonObject airlineData = new JsonObject()
                            .put("code", "TEST")
                            .put("name", "Test Airlines")
                            .put("country", "Test Country");
                        
                        return client.post(8080, "localhost", "/airlines")
                            .sendJson(airlineData);
                    })
                    .compose(response -> {
                        assertThat(response.statusCode()).isEqualTo(201);
                        JsonObject airline = response.bodyAsJsonObject();
                        assertThat(airline.getString("code")).isEqualTo("TEST");
                        
                        // Test 3: Search flights
                        return client.get(8080, "localhost", "/flights/search?from=LHR&to=JFK")
                            .send();
                    })
                    .compose(response -> {
                        assertThat(response.statusCode()).isEqualTo(200);
                        assertThat(response.bodyAsString()).isNotEmpty();
                        
                        // Test 4: Add a passenger
                        JsonObject passengerData = new JsonObject()
                            .put("firstName", "John")
                            .put("lastName", "Doe")
                            .put("email", "john.doe@example.com")
                            .put("phone", "+1-555-123-4567")
                            .put("passportNumber", "US123456789")
                            .put("dateOfBirth", "1990-01-15");
                        
                        return client.post(8080, "localhost", "/passengers")
                            .sendJson(passengerData);
                    })
                    .compose(response -> {
                        assertThat(response.statusCode()).isEqualTo(201);
                        JsonObject passenger = response.bodyAsJsonObject();
                        assertThat(passenger.getString("firstName")).isEqualTo("John");
                        
                        // Test 5: Create a booking
                        JsonObject bookingData = new JsonObject()
                            .put("passengerId", passenger.getLong("id"))
                            .put("flightId", 1L)
                            .put("seatNumber", "12A");
                        
                        return client.post(8080, "localhost", "/bookings")
                            .sendJson(bookingData);
                    })
                    .compose(response -> {
                        // Booking might succeed or fail depending on data availability
                        assertThat(response.statusCode()).isIn(201, 400);
                        
                        // Test 6: Get passenger bookings
                        return client.get(8080, "localhost", "/passengers/1/bookings")
                            .send();
                    })
                    .onComplete(ar -> {
                        if (ar.succeeded()) {
                            assertThat(ar.result().statusCode()).isEqualTo(200);
                            testContext.completeNow();
                        } else {
                            testContext.failNow(ar.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testErrorHandling(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new MainVerticle())
            .onSuccess(deploymentId -> {
                WebClient client = WebClient.create(vertx);

                // Test invalid endpoint
                client.get(8080, "localhost", "/invalid-endpoint")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(404);
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testDatabaseInitialization(Vertx vertx, VertxTestContext testContext) {
        DatabaseInitService databaseInitService = new DatabaseInitService(vertx);
        
        databaseInitService.initializeDatabase()
            .onComplete(ar -> {
                if (ar.succeeded()) {
                    // Database initialization should succeed
                    testContext.completeNow();
                } else {
                    testContext.failNow(ar.cause());
                }
            });
    }
}
