package com.dvtsoftware.airline.booking.model;

import java.time.LocalDateTime;

/**
 * Domain model representing a passenger.
 */
public class Passenger {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passportNumber;
    private String nationality;
    private LocalDateTime createdAt;

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, String email, String phone,
                     String passportNumber, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
    }

    public Passenger(Long id, String firstName, String lastName, String email, String phone,
                     String passportNumber, String nationality, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


