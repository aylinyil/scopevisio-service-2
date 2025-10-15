package com.insurance.premium_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representing a vehicle type and its associated risk factor.
 *     
 * This entity maps to the 'vehicle' table in the database and stores
 * vehicle type classifications along with their corresponding factors
 * used in premium calculations. Different vehicle types carry different
 * risk profiles based on factors such as safety ratings and repair costs.
 * 
 * @author Aylin Yilmaz
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {
    
    /**
     * Unique identifier for the vehicle type record.
     * Generated automatically using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type or category of the vehicle.
     *     
     * This field uniquely identifies the vehicle type within the system
     * and is used for lookups during premium calculations.
     */
    @Column(name = "vehicle_type")
    private String vehicleType;

    /**
     * The risk factor associated with this vehicle type for premium calculations.
     *     
     */
    @Column(name = "vehicle_factor")
    private Double vehicleFactor;

    /**
     * Gets the unique identifier of this vehicle type.
     * 
     * @return the vehicle type ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the vehicle type name.
     * 
     * @return the vehicle type (e.g., "sedan", "SUV")
     */
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * Gets the risk factor for this vehicle type.
     * 
     * @return the vehicle risk factor
     */
    public Double getVehicleFactor() {
        return vehicleFactor;
    }
}
