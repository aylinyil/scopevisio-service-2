package com.insurance.premium_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.premium_service.entity.Vehicle;

/**
 * Repository interface for managing Vehicle entities.
 *     
 * This repository provides data access operations for vehicle type records,
 * including the ability to find vehicles by their type classification. It extends
 * JpaRepository to inherit standard CRUD operations and query methods.
 * 
 *     The repository is used during premium calculations to retrieve
 * vehicle-specific risk factors based on the vehicle type, which are then
 * applied as multipliers in the premium calculation formula.
 * 
 * @author Aylin Yilmaz
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    /**
     * Finds a vehicle record by its vehicle type.
     *     
     * This method performs a case-sensitive search for the exact vehicle
     * type in the database. The vehicle type should match exactly as stored
     * in the database.
     * 
     * @param vehicleType the vehicle type to search for
     * @return an Optional containing the Vehicle entity if found, or empty if not found
     */
    Optional<Vehicle> findByVehicleType(String vehicleType);
    
}
