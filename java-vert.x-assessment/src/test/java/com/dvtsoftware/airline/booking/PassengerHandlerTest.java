package com.dvtsoftware.airline.booking;

import com.dvtsoftware.airline.booking.handler.PassengerHandler;
import com.dvtsoftware.airline.booking.model.Passenger;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith({VertxExtension.class, MockitoExtension.class})
@DisplayName("Passenger Handler Tests")
class PassengerHandlerTest {

    @Mock
    private IDatabaseService databaseService;

    @Mock
    private RoutingContext routingContext;

    private PassengerHandler passengerHandler;

    @BeforeEach
    void setUp() {
        passengerHandler = new PassengerHandler(databaseService);
    }

    @Test
    @DisplayName("Create passenger with valid data should succeed")
    void testCreatePassengerSuccess(Vertx vertx, VertxTestContext testContext) {
        JsonObject body = new JsonObject()
                .put("firstName", "John")
                .put("lastName", "Doe")
                .put("email", "john.doe@example.com")
                .put("phone", "+1-555-0100")
                .put("passportNumber", "US123456")
                .put("nationality", "United States");

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.ext.web.RequestBody requestBody = mock(io.vertx.ext.web.RequestBody.class);
        when(ctx.body()).thenReturn(requestBody);
        when(requestBody.asJsonObject()).thenReturn(body);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues
        // The test will still work without explicitly mocking end()

        Passenger passenger = new Passenger(1L, "John", "Doe", "john.doe@example.com",
                "+1-555-0100", "US123456", "United States", null);
        when(databaseService.createPassenger(any(Passenger.class))).thenReturn(Future.succeededFuture(passenger));

        passengerHandler.createPassenger(ctx);
        
        verify(databaseService, timeout(1000)).createPassenger(any(Passenger.class));
        testContext.completeNow();
    }

    @Test
    @DisplayName("Create passenger with missing required fields should fail")
    void testCreatePassengerMissingFields(Vertx vertx, VertxTestContext testContext) {
        JsonObject body = new JsonObject()
                .put("firstName", "John");

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.ext.web.RequestBody requestBody = mock(io.vertx.ext.web.RequestBody.class);
        when(ctx.body()).thenReturn(requestBody);
        when(requestBody.asJsonObject()).thenReturn(body);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues
        // The test will still work without explicitly mocking end()

        passengerHandler.createPassenger(ctx);
        
        verify(databaseService, never()).createPassenger(any(Passenger.class));
        testContext.completeNow();
    }
}

