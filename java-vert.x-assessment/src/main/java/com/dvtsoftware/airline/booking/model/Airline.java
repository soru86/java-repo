package com.dvtsoftware.airline.booking.model;

import java.time.LocalDateTime;

/**
 * Domain model representing an airline company.
 */
public class Airline {
    private Long id;
    private String code;
    private String name;
    private String country;
    private LocalDateTime createdAt;

    public Airline() {
    }

    public Airline(String code, String name, String country) {
        this.code = code;
        this.name = name;
        this.country = country;
    }

    public Airline(Long id, String code, String name, String country, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.country = country;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}


