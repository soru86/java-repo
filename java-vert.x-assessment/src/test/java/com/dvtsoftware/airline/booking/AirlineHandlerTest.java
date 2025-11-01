package com.dvtsoftware.airline.booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.dvtsoftware.airline.booking.handler.AirlineHandler;
import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class AirlineHandlerTest {

    private DatabaseAppService databaseAppService;
    private AirlineHandler airlineHandler;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        databaseAppService = new DatabaseAppService(vertx);
        airlineHandler = new AirlineHandler(databaseAppService);
        testContext.completeNow();
    }

    @Test
    void testAddAirline(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/airlines").handler(airlineHandler::addAirline);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject airlineData = new JsonObject()
                    .put("code", "TEST")
                    .put("name", "Test Airlines")
                    .put("country", "Test Country");

                client.post(8080, "localhost", "/airlines")
                    .sendJson(airlineData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(201);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("code")).isEqualTo("TEST");
                            assertThat(responseBody.getString("name")).isEqualTo("Test Airlines");
                            assertThat(responseBody.getString("country")).isEqualTo("Test Country");
                            assertThat(responseBody.getLong("id")).isNotNull();
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }

    @Test
    void testAddAirlineMissingFields(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/airlines").handler(airlineHandler::addAirline);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8081)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject incompleteData = new JsonObject()
                    .put("code", "TEST")
                    .put("name", "Test Airlines");
                    // Missing country field

                client.post(8081, "localhost", "/airlines")
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
    void testGetAllAirlines(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.get("/airlines").handler(airlineHandler::listAirlines);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8082)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);

                client.get(8082, "localhost", "/airlines")
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
}