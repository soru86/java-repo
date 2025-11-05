package com.dvtsoftware.airline.booking;

import com.dvtsoftware.airline.booking.handler.BookingsHandler;
import com.dvtsoftware.airline.booking.model.Booking;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({JacksonConfigurationExtension.class, VertxExtension.class, MockitoExtension.class})
@DisplayName("Bookings Handler Tests")
class BookingsHandlerTest {

    @Mock
    private IDatabaseService databaseService;

    @Mock
    private RoutingContext routingContext;

    private BookingsHandler bookingsHandler;

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
        bookingsHandler = new BookingsHandler(databaseService);
    }

    @Test
    @DisplayName("Create booking with valid data should succeed")
    void testCreateBookingSuccess(Vertx vertx, VertxTestContext testContext) {
        JsonObject body = new JsonObject()
                .put("passengerId", 1)
                .put("flightId", 1)
                .put("seatNumber", "12A");

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.ext.web.RequestBody requestBody = mock(io.vertx.ext.web.RequestBody.class);
        when(ctx.body()).thenReturn(requestBody);
        when(requestBody.asJsonObject()).thenReturn(body);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        Booking booking = new Booking(1L, 1L, 1L, "BOOK123", "12A",
                "CONFIRMED", LocalDateTime.now(), null);
        when(databaseService.createBooking(any(Booking.class))).thenReturn(Future.succeededFuture(booking));

        bookingsHandler.createBooking(ctx);
        
        verify(databaseService, timeout(1000)).createBooking(any(Booking.class));
        testContext.completeNow();
    }

    @Test
    @DisplayName("Create booking with missing fields should fail")
    void testCreateBookingMissingFields(Vertx vertx, VertxTestContext testContext) {
        JsonObject body = new JsonObject()
                .put("passengerId", 1);

        RoutingContext ctx = mock(RoutingContext.class);
        io.vertx.ext.web.RequestBody requestBody = mock(io.vertx.ext.web.RequestBody.class);
        when(ctx.body()).thenReturn(requestBody);
        when(requestBody.asJsonObject()).thenReturn(body);
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        bookingsHandler.createBooking(ctx);
        
        verify(databaseService, never()).createBooking(any(Booking.class));
        testContext.completeNow();
    }

    @Test
    @DisplayName("Get booking by ID should succeed")
    void testGetBookingByIdSuccess(Vertx vertx, VertxTestContext testContext) {
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

        Booking booking = new Booking(1L, 1L, 1L, "BOOK123", "12A",
                "CONFIRMED", LocalDateTime.of(2024, 6, 15, 10, 0), null);
        when(databaseService.getBookingById(1L)).thenReturn(Future.succeededFuture(booking));

        bookingsHandler.getBookingById(ctx);
        
        verify(databaseService, timeout(1000)).getBookingById(1L);
        testContext.completeNow();
    }

    @Test
    @DisplayName("Cancel booking should succeed")
    void testCancelBookingSuccess(Vertx vertx, VertxTestContext testContext) {
        RoutingContext ctx = mock(RoutingContext.class);
        when(ctx.pathParam("id")).thenReturn("1");
        io.vertx.core.http.HttpServerResponse response = mock(io.vertx.core.http.HttpServerResponse.class);
        when(ctx.response()).thenReturn(response);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        when(response.putHeader(anyString(), anyString())).thenReturn(response);
        // Don't mock end() - it may have multiple overloads that cause issues

        when(databaseService.cancelBooking(1L)).thenReturn(Future.succeededFuture());

        bookingsHandler.cancelBooking(ctx);
        
        verify(databaseService, timeout(1000)).cancelBooking(1L);
        testContext.completeNow();
    }

    @Test
    @DisplayName("Get bookings by passenger ID should succeed")
    void testGetBookingsByPassengerIdSuccess(Vertx vertx, VertxTestContext testContext) {
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

        List<Booking> bookings = Arrays.asList(
                new Booking(1L, 1L, 1L, "BOOK123", "12A",
                        "CONFIRMED", LocalDateTime.of(2024, 6, 15, 10, 0), null)
        );
        when(databaseService.getBookingsByPassengerId(1L)).thenReturn(Future.succeededFuture(bookings));

        bookingsHandler.getBookingsByPassengerId(ctx);
        
        verify(databaseService, timeout(1000)).getBookingsByPassengerId(1L);
        testContext.completeNow();
    }
}

