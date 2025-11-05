-- Airline Booking System Database Schema
-- This schema supports a global airline reservation platform

-- Airlines Table
CREATE TABLE IF NOT EXISTS airlines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Flights Table
CREATE TABLE IF NOT EXISTS flights (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    airline_id BIGINT NOT NULL,
    flight_number VARCHAR(10) NOT NULL,
    origin VARCHAR(3) NOT NULL,
    destination VARCHAR(3) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    total_seats INT NOT NULL DEFAULT 0,
    available_seats INT NOT NULL DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (airline_id) REFERENCES airlines(id) ON DELETE CASCADE,
    CONSTRAINT valid_seats CHECK (available_seats >= 0 AND available_seats <= total_seats)
);

-- Passengers Table
CREATE TABLE IF NOT EXISTS passengers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    passport_number VARCHAR(50),
    nationality VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    flight_id BIGINT NOT NULL,
    booking_reference VARCHAR(20) NOT NULL UNIQUE,
    seat_number VARCHAR(10),
    booking_status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE,
    CONSTRAINT valid_status CHECK (booking_status IN ('CONFIRMED', 'CANCELLED', 'COMPLETED'))
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_flights_airline ON flights(airline_id);
CREATE INDEX IF NOT EXISTS idx_flights_route ON flights(origin, destination);
CREATE INDEX IF NOT EXISTS idx_bookings_passenger ON bookings(passenger_id);
CREATE INDEX IF NOT EXISTS idx_bookings_flight ON bookings(flight_id);
CREATE INDEX IF NOT EXISTS idx_bookings_reference ON bookings(booking_reference);
CREATE INDEX IF NOT EXISTS idx_passengers_email ON passengers(email);


