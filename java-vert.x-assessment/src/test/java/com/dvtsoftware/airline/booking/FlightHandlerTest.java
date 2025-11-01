package com.dvtsoftware.airline.booking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.dvtsoftware.airline.booking.handler.FlightHandler;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class FlightHandlerTest {

    private DatabaseAppService databaseAppService;
    private FlightHandler flightHandler;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        databaseAppService = new DatabaseAppService(vertx);
        flightHandler = new FlightHandler(databaseAppService);
        testContext.completeNow();
    }

    @Test
    void testAddFlight(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/flights").handler(flightHandler::addFlight);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8083)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject flightData = new JsonObject()
                    .put("flightNumber", "TEST001")
                    .put("airlineId", 1L)
                    .put("departureAirport", "LHR")
                    .put("arrivalAirport", "JFK")
                    .put("departureTime", "2024-12-01T10:00:00")
                    .put("arrivalTime", "2024-12-01T18:00:00")
                    .put("totalSeats", 300)
                    .put("availableSeats", 300)
                    .put("price", 599.99);

                client.post(8083, "localhost", "/flights")
                    .sendJson(flightData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(201);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("flightNumber")).isEqualTo("TEST001");
                            assertThat(responseBody.getLong("airlineId")).isEqualTo(1L);
                            assertThat(responseBody.getString("departureAirport")).isEqualTo("LHR");
                            assertThat(responseBody.getString("arrivalAirport")).isEqualTo("JFK");
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testAddFlightMissingFields(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/flights").handler(flightHandler::addFlight);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8084)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject incompleteData = new JsonObject()
                    .put("flightNumber", "TEST001")
                    .put("airlineId", 1L);
                    // Missing required fields

                client.post(8084, "localhost", "/flights")
                    .sendJson(incompleteData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(400);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("error")).isEqualTo("Missing required fields");
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testGetFlightById(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/flights/:id").handler(flightHandler::getFlightById);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8085)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8085, "localhost", "/flights/1")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            // Should return 404 for non-existent flight or 200 for existing flight
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
    void testSearchFlights(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/flights/search").handler(flightHandler::searchFlights);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8086)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8086, "localhost", "/flights/search?from=LHR&to=JFK")
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
    void testSearchFlightsMissingParams(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/flights/search").handler(flightHandler::searchFlights);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8087)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8087, "localhost", "/flights/search")
                    .send()
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(400);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("error")).contains("Missing");
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }
}
