-- Sample Data for Airline Booking System
-- Global airlines and flight data

-- Insert Airlines
INSERT INTO airlines (code, name, country) VALUES
('EK', 'Emirates', 'United Arab Emirates'),
('QR', 'Qatar Airways', 'Qatar'),
('ET', 'Ethiopian Airlines', 'Ethiopia'),
('SQ', 'Singapore Airlines', 'Singapore'),
('BA', 'British Airways', 'United Kingdom'),
('LH', 'Lufthansa', 'Germany'),
('AF', 'Air France', 'France'),
('AA', 'American Airlines', 'United States'),
('DL', 'Delta Air Lines', 'United States'),
('UA', 'United Airlines', 'United States');

-- Insert Flights
INSERT INTO flights (airline_id, flight_number, origin, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES
(1, 'EK201', 'DXB', 'LHR', '2024-06-15 08:00:00', '2024-06-15 12:30:00', 300, 250, 850.00),
(1, 'EK202', 'LHR', 'DXB', '2024-06-15 14:00:00', '2024-06-15 23:30:00', 300, 280, 900.00),
(2, 'QR701', 'DOH', 'JFK', '2024-06-16 02:00:00', '2024-06-16 08:30:00', 350, 320, 1200.00),
(2, 'QR702', 'JFK', 'DOH', '2024-06-16 22:00:00', '2024-06-17 17:30:00', 350, 340, 1250.00),
(3, 'ET500', 'ADD', 'DXB', '2024-06-17 10:00:00', '2024-06-17 14:00:00', 250, 200, 450.00),
(3, 'ET501', 'DXB', 'ADD', '2024-06-17 18:00:00', '2024-06-17 23:00:00', 250, 230, 480.00),
(4, 'SQ321', 'SIN', 'LHR', '2024-06-18 00:30:00', '2024-06-18 06:00:00', 280, 250, 1100.00),
(4, 'SQ322', 'LHR', 'SIN', '2024-06-18 22:00:00', '2024-06-19 17:30:00', 280, 270, 1150.00),
(5, 'BA100', 'LHR', 'JFK', '2024-06-19 09:00:00', '2024-06-19 12:00:00', 320, 290, 950.00),
(5, 'BA101', 'JFK', 'LHR', '2024-06-19 19:00:00', '2024-06-20 06:30:00', 320, 300, 980.00),
(6, 'LH400', 'FRA', 'DXB', '2024-06-20 13:00:00', '2024-06-20 21:30:00', 300, 280, 750.00),
(6, 'LH401', 'DXB', 'FRA', '2024-06-20 23:00:00', '2024-06-21 05:30:00', 300, 295, 780.00),
(7, 'AF300', 'CDG', 'JFK', '2024-06-21 11:00:00', '2024-06-21 13:30:00', 310, 285, 920.00),
(7, 'AF301', 'JFK', 'CDG', '2024-06-21 20:00:00', '2024-06-22 09:30:00', 310, 300, 940.00),
(8, 'AA200', 'DFW', 'LHR', '2024-06-22 15:00:00', '2024-06-22 05:00:00', 330, 300, 880.00),
(8, 'AA201', 'LHR', 'DFW', '2024-06-22 10:00:00', '2024-06-22 14:00:00', 330, 310, 890.00),
(9, 'DL500', 'ATL', 'DXB', '2024-06-23 16:00:00', '2024-06-24 11:30:00', 340, 320, 1050.00),
(9, 'DL501', 'DXB', 'ATL', '2024-06-24 01:00:00', '2024-06-24 08:30:00', 340, 335, 1070.00),
(10, 'UA600', 'SFO', 'SIN', '2024-06-25 12:00:00', '2024-06-26 05:30:00', 320, 295, 1300.00),
(10, 'UA601', 'SIN', 'SFO', '2024-06-26 01:00:00', '2024-06-26 09:30:00', 320, 305, 1320.00);

-- Insert Sample Passengers
INSERT INTO passengers (first_name, last_name, email, phone, passport_number, nationality) VALUES
('Ahmed', 'Al-Mansoori', 'ahmed.almansoori@example.com', '+971-50-1234567', 'UAE123456', 'United Arab Emirates'),
('Sarah', 'Johnson', 'sarah.johnson@example.com', '+1-212-555-0101', 'US123456789', 'United States'),
('Mohammed', 'Hassan', 'mohammed.hassan@example.com', '+974-33123456', 'QAT789012', 'Qatar'),
('Priya', 'Patel', 'priya.patel@example.com', '+65-91234567', 'SGP345678', 'Singapore'),
('James', 'Smith', 'james.smith@example.com', '+44-20-7946-0958', 'GBR901234', 'United Kingdom'),
('Fatima', 'Ibrahim', 'fatima.ibrahim@example.com', '+251-911-234567', 'ETH567890', 'Ethiopia'),
('Jean', 'Dubois', 'jean.dubois@example.com', '+33-1-42-86-83-26', 'FRA234567', 'France'),
('Hans', 'Mueller', 'hans.mueller@example.com', '+49-30-12345678', 'DEU890123', 'Germany');

-- Insert Sample Bookings
INSERT INTO bookings (passenger_id, flight_id, booking_reference, seat_number, booking_status) VALUES
(1, 1, 'EK201-001', '12A', 'CONFIRMED'),
(2, 3, 'QR701-002', '25B', 'CONFIRMED'),
(3, 5, 'ET500-003', '8C', 'CONFIRMED'),
(4, 7, 'SQ321-004', '15D', 'CONFIRMED'),
(5, 9, 'BA100-005', '22A', 'CONFIRMED'),
(6, 11, 'LH400-006', '18B', 'CONFIRMED'),
(7, 13, 'AF300-007', '30C', 'CONFIRMED'),
(8, 15, 'AA200-008', '5D', 'CONFIRMED');


