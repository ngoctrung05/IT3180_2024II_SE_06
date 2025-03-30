package com.IT3180.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "resident")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "relationship_with_householder", nullable = false)
    private String relationshipWithHouseholder;

    @Column(name = "cccd", unique = true, nullable = true, length = 12)
    private String cccd;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = false)
    private String status; // "Còn ở" hoặc "Đã rời đi"

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    @JsonIgnore
    private Apartment apartment;

    // Constructors
    public Resident() {}

    public Resident(String name, String relationshipWithHouseholder, String cccd, String email, String status, Apartment apartment) {
        this.name = name;
        this.relationshipWithHouseholder = relationshipWithHouseholder;
        this.cccd = cccd;
        this.email = email;
        this.status = status;
        this.apartment = apartment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationshipWithHouseholder() {
        return relationshipWithHouseholder;
    }

    public void setRelationshipWithHouseholder(String relationshipWithHouseholder) {
        this.relationshipWithHouseholder = relationshipWithHouseholder;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
