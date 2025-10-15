package com.insurance.premium_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_factor")
    private Double vehicleFactor;

    public Long getId() {
        return id;
    }

    public String getVehicleType() {
        return vehicleType;
    }
    public Double getVehicleFactor() {
        return vehicleFactor;
    }
}
