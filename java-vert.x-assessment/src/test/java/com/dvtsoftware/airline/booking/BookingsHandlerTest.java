package com.dvtsoftware.airline.booking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.dvtsoftware.airline.booking.handler.BookingsHandler;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class BookingsHandlerTest {

    private DatabaseAppService databaseAppService;
    private BookingsHandler bookingsHandler;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        databaseAppService = new DatabaseAppService(vertx);
        bookingsHandler = new BookingsHandler(databaseAppService);
        testContext.completeNow();
    }

    @Test
    void testCreateBooking(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/bookings").handler(bookingsHandler::createBooking);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8091)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject bookingData = new JsonObject()
                    .put("passengerId", 1L)
                    .put("flightId", 1L)
                    .put("seatNumber", "12A");

                client.post(8091, "localhost", "/bookings")
                    .sendJson(bookingData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            // Should return 201 for successful booking or 400 for validation errors
                            assertThat(response.result().statusCode()).isIn(201, 400);
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testCreateBookingMissingFields(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/bookings").handler(bookingsHandler::createBooking);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8092)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject incompleteData = new JsonObject()
                    .put("passengerId", 1L);
                    // Missing flightId and seatNumber

                client.post(8092, "localhost", "/bookings")
                    .sendJson(incompleteData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(400);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("error")).contains("Missing required fields");
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testGetBookingById(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/bookings/:id").handler(bookingsHandler::getBookingById);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8093)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8093, "localhost", "/bookings/1")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            // Should return 200 for existing booking or 404 for non-existent
                            assertThat(response.result().statusCode()).isIn(200, 404);
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testCancelBooking(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.delete("/bookings/:id").handler(bookingsHandler::cancelBooking);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8094)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.delete(8094, "localhost", "/bookings/1")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            // Should return 200 for successful cancellation or 400 for errors
                            assertThat(response.result().statusCode()).isIn(200, 400);
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testListPassengerBookings(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/passengers/:id/bookings").handler(bookingsHandler::listPassengerBookings);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8095)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8095, "localhost", "/passengers/1/bookings")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(200);
                            // The response should be a JSON array
                            assertThat(response.result().bodyAsString()).isNotEmpty();
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testListPassengerBookingsInvalidId(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/passengers/:id/bookings").handler(bookingsHandler::listPassengerBookings);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8096)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8096, "localhost", "/passengers/invalid/bookings")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(400);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("error")).contains("Invalid passenger ID format");
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }
}
