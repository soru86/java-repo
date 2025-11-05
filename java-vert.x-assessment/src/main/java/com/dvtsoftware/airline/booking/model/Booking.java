package com.dvtsoftware.airline.booking.model;

import java.time.LocalDateTime;

/**
 * Domain model representing a booking.
 */
public class Booking {
    private Long id;
    private Long passengerId;
    private Long flightId;
    private String bookingReference;
    private String seatNumber;
    private String bookingStatus;
    private LocalDateTime bookingDate;
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(Long passengerId, Long flightId, String bookingReference,
            String seatNumber, String bookingStatus) {
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.bookingReference = bookingReference;
        this.seatNumber = seatNumber;
        this.bookingStatus = bookingStatus;
    }

    public Booking(Long id, Long passengerId, Long flightId, String bookingReference,
            String seatNumber, String bookingStatus,
            LocalDateTime bookingDate, LocalDateTime createdAt) {
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.bookingReference = bookingReference;
        this.seatNumber = seatNumber;
        this.bookingStatus = bookingStatus;
        this.bookingDate = bookingDate;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
