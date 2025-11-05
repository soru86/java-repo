package com.dvtsoftware.airline.booking;

import com.dvtsoftware.airline.booking.handler.AirlineHandler;
import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.RoutingContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith({VertxExtension.class, MockitoExtension.class})
@DisplayName("Airline Handler Tests")
class AirlineHandlerTest {

    @Mock
    private IDatabaseService databaseService;

    @Mock
    private RoutingContext routingContext;

    private AirlineHandler airlineHandler;

    @BeforeEach
    void setUp() {
        airlineHandler = new AirlineHandler(databaseService);
    }

    @Test
    @DisplayName("Create airline with valid data should succeed")
    void testCreateAirlineSuccess(Vertx vertx, VertxTestContext testContext) {
        JsonObject body = new JsonObject()
                .put("code", "VS")
                .put("name", "Virgin Atlantic")
                .put("country", "United Kingdom");

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.ext.web.RequestBody requestBody = mock(io.vertx.ext.web.RequestBody.class);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        
        when(ctx.body()).thenReturn(requestBody);
        when(requestBody.asJsonObject()).thenReturn(body);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        Airline airline = new Airline(1L, "VS", "Virgin Atlantic", "United Kingdom", null);
        when(databaseService.createAirline(any(Airline.class))).thenReturn(Future.succeededFuture(airline));

        airlineHandler.createAirline(ctx);
        
        verify(databaseService, timeout(1000)).createAirline(any(Airline.class));
        testContext.completeNow();
    }

    @Test
    @DisplayName("Create airline with missing fields should fail")
    void testCreateAirlineMissingFields(Vertx vertx, VertxTestContext testContext) {
        JsonObject body = new JsonObject()
                .put("code", "VS");

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.ext.web.RequestBody requestBody = mock(io.vertx.ext.web.RequestBody.class);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.body()).thenReturn(requestBody);
        when(requestBody.asJsonObject()).thenReturn(body);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        airlineHandler.createAirline(ctx);
        
        verify(databaseService, never()).createAirline(any(Airline.class));
        testContext.completeNow();
    }

    @Test
    @DisplayName("Get all airlines should succeed")
    void testGetAllAirlinesSuccess(Vertx vertx, VertxTestContext testContext) {
        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        List<Airline> airlines = Arrays.asList(
                new Airline(1L, "EK", "Emirates", "UAE", null),
                new Airline(2L, "QR", "Qatar Airways", "Qatar", null)
        );
        when(databaseService.getAllAirlines()).thenReturn(Future.succeededFuture(airlines));

        airlineHandler.getAllAirlines(ctx);
        
        verify(databaseService, timeout(1000)).getAllAirlines();
        testContext.completeNow();
    }
}

