package com.dvtsoftware.airline.booking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.dvtsoftware.airline.booking.handler.PassengerHandler;
import com.dvtsoftware.airline.booking.service.DatabaseAppService;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class PassengerHandlerTest {

    private DatabaseAppService databaseAppService;
    private PassengerHandler passengerHandler;

    @BeforeEach
    void setUp(Vertx vertx, VertxTestContext testContext) {
        databaseAppService = new DatabaseAppService(vertx);
        passengerHandler = new PassengerHandler(databaseAppService);
        testContext.completeNow();
    }

    @Test
    void testAddPassenger(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/passengers").handler(passengerHandler::addPassenger);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8088)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject passengerData = new JsonObject()
                    .put("firstName", "John")
                    .put("lastName", "Doe")
                    .put("email", "john.doe@example.com")
                    .put("phone", "+1-555-123-4567")
                    .put("passportNumber", "US123456789")
                    .put("dateOfBirth", "1990-01-15");

                client.post(8088, "localhost", "/passengers")
                    .sendJson(passengerData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(201);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("firstName")).isEqualTo("John");
                            assertThat(responseBody.getString("lastName")).isEqualTo("Doe");
                            assertThat(responseBody.getString("email")).isEqualTo("john.doe@example.com");
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
    void testAddPassengerMissingFields(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/passengers").handler(passengerHandler::addPassenger);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8089)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject incompleteData = new JsonObject()
                    .put("firstName", "John")
                    .put("lastName", "Doe");
                    // Missing email field

                client.post(8089, "localhost", "/passengers")
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
    void testAddPassengerInvalidDate(Vertx vertx, VertxTestContext testContext) {
        Router router = Router.router(vertx);
        router.post("/passengers").handler(passengerHandler::addPassenger);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8090)
            .onSuccess(server -> {
                WebClient client = WebClient.create(vertx);
                
                JsonObject passengerData = new JsonObject()
                    .put("firstName", "John")
                    .put("lastName", "Doe")
                    .put("email", "john.doe@example.com")
                    .put("dateOfBirth", "invalid-date");

                client.post(8090, "localhost", "/passengers")
                    .sendJson(passengerData)
                    .onComplete(response -> {
                        if (response.succeeded()) {
                            assertThat(response.result().statusCode()).isEqualTo(400);
                            JsonObject responseBody = response.result().bodyAsJsonObject();
                            assertThat(responseBody.getString("error")).contains("Invalid date format");
                            testContext.completeNow();
                        } else {
                            testContext.failNow(response.cause());
                        }
                    });
            })
            .onFailure(testContext::failNow);
    }
}
