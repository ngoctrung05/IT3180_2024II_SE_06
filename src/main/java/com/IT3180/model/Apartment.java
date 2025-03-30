package com.IT3180.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    private Long id;

    @Column(name = "id_householder", nullable = true)
    private Long idHouseholder;

    @Column(name = "phone", length = 15, nullable = true)
    private String phone;

    @Column(name = "area", nullable = false)
    private Double area;

    @OneToMany(mappedBy = "apartment")
    private List<Resident> residents;

    @OneToOne(mappedBy = "apartment")
    private User user;

    // Constructors
    public Apartment() {}

    public Apartment(Long id, Long idHouseholder, String phone, Double area) {
        this.id = id;
        this.idHouseholder = idHouseholder;
        this.phone = phone;
        this.area = area;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdHouseholder() {
        return idHouseholder;
    }

    public void setIdHouseholder(Long idHouseholder) {
        this.idHouseholder = idHouseholder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getArea() {
        return area;
    }

    public User getUser() {
        return user;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

