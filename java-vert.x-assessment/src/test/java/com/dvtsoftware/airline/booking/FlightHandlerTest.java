package com.dvtsoftware.airline.booking;

import com.dvtsoftware.airline.booking.handler.FlightHandler;
import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.service.IDatabaseService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({ JacksonConfigurationExtension.class, VertxExtension.class, MockitoExtension.class })
@DisplayName("Flight Handler Tests")
class FlightHandlerTest {

    @Mock
    private IDatabaseService databaseService;

    @Mock
    private RoutingContext routingContext;

    private FlightHandler flightHandler;

    @BeforeAll
    static void setUpAll() {
        // Ensure Jackson is configured before any tests run
        JacksonConfigurationExtension.ensureConfigured();
        TestConfiguration.configure();
    }

    @BeforeEach
    void setUp() {
        // Ensure Jackson is configured before each test
        JacksonConfigurationExtension.ensureConfigured();
        TestConfiguration.configure();
        flightHandler = new FlightHandler(databaseService);
    }

    @Test
    @DisplayName("Get flight by ID should succeed")
    void testGetFlightByIdSuccess(Vertx vertx, VertxTestContext testContext) {
        // Ensure Jackson is configured right before test execution
        JacksonConfigurationExtension.ensureConfigured();
        TestConfiguration.configure();

        RoutingContext ctx = mock(RoutingContext.class);
        when(ctx.pathParam("id")).thenReturn("1");
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        Flight flight = new Flight(1L, 1L, "EK201", "DXB", "LHR",
                LocalDateTime.now(), LocalDateTime.now().plusHours(4),
                300, 250, BigDecimal.valueOf(850.0), null);
        when(databaseService.getFlightById(1L)).thenReturn(Future.succeededFuture(flight));

        flightHandler.getFlightById(ctx);

        verify(databaseService, timeout(1000)).getFlightById(1L);
        testContext.completeNow();
    }

    @Test
    @DisplayName("Get flight by ID with invalid format should fail")
    void testGetFlightByIdInvalidFormat(Vertx vertx, VertxTestContext testContext) {
        RoutingContext ctx = mock(RoutingContext.class);
        when(ctx.pathParam("id")).thenReturn("invalid");
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        flightHandler.getFlightById(ctx);

        verify(databaseService, never()).getFlightById(any());
        testContext.completeNow();
    }

    @Test
    @DisplayName("Search flights should succeed")
    void testSearchFlightsSuccess(Vertx vertx, VertxTestContext testContext) {
        // Ensure Jackson is configured right before test execution
        JacksonConfigurationExtension.ensureConfigured();
        TestConfiguration.configure();

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.core.MultiMap queryParams = mock(io.vertx.core.MultiMap.class);
        when(ctx.queryParams()).thenReturn(queryParams);
        when(queryParams.get("from")).thenReturn("DXB");
        when(queryParams.get("to")).thenReturn("LHR");
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        List<Flight> flights = Arrays.asList(
                new Flight(1L, 1L, "EK201", "DXB", "LHR",
                        LocalDateTime.now(), LocalDateTime.now().plusHours(4),
                        300, 250, BigDecimal.valueOf(850.0), null));
        when(databaseService.searchFlights("DXB", "LHR")).thenReturn(Future.succeededFuture(flights));

        flightHandler.searchFlights(ctx);

        verify(databaseService, timeout(1000)).searchFlights("DXB", "LHR");
        testContext.completeNow();
    }

    @Test
    @DisplayName("Search flights with missing parameters should fail")
    void testSearchFlightsMissingParams(Vertx vertx, VertxTestContext testContext) {
        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.core.MultiMap queryParams = mock(io.vertx.core.MultiMap.class);
        when(ctx.queryParams()).thenReturn(queryParams);
        when(queryParams.get("from")).thenReturn(null);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        flightHandler.searchFlights(ctx);

        verify(databaseService, never()).searchFlights(any(), any());
        testContext.completeNow();
    }
}
