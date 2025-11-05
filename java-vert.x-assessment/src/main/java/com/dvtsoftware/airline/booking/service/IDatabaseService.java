package com.dvtsoftware.airline.booking.service;

import com.dvtsoftware.airline.booking.model.Airline;
import com.dvtsoftware.airline.booking.model.Booking;
import com.dvtsoftware.airline.booking.model.Flight;
import com.dvtsoftware.airline.booking.model.Passenger;
import io.vertx.core.Future;

import java.util.List;

/**
 * Interface for database service operations.
 * This interface allows for easier testing and mocking.
 */
public interface IDatabaseService {
    Future<Void> initializeDatabase();
    Future<Airline> createAirline(Airline airline);
    Future<List<Airline>> getAllAirlines();
    Future<Airline> getAirlineById(Long id);
    Future<Flight> createFlight(Flight flight);
    Future<Flight> getFlightById(Long id);
    Future<List<Flight>> searchFlights(String from, String to);
    Future<Passenger> createPassenger(Passenger passenger);
    Future<Passenger> getPassengerById(Long id);
    Future<Booking> createBooking(Booking booking);
    Future<Booking> getBookingById(Long id);
    Future<Void> cancelBooking(Long id);
    Future<List<Booking>> getBookingsByPassengerId(Long passengerId);
}


