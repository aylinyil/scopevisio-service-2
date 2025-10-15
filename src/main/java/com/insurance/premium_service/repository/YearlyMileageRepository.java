package com.insurance.premium_service.repository;
import java.math.BigDecimal;
import com.insurance.premium_service.entity.YearlyMileage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing YearlyMileage entities.
 *     
 * This repository provides data access operations for yearly mileage range records,
 * including custom query methods to find mileage ranges that contain a specific
 * mileage value. It extends JpaRepository to inherit standard CRUD operations.
 * 
 *     The repository is used during premium calculations to determine which mileage
 * range a customer's yearly mileage falls into, and retrieve the corresponding
 * risk factor for premium calculation.
 * 
 * @author Aylin Yilmaz
 */
@Repository
public interface YearlyMileageRepository extends JpaRepository<YearlyMileage, Long>{
    
    /**
     * Finds a yearly mileage range that contains the specified mileage value.
     *     
     * This method uses a custom JPQL query to find the mileage range where the
     * provided yearly mileage falls between the 'yearlyMileageFrom' (inclusive)
     * and 'yearlyMileageTo' (inclusive) bounds.
     * 
     *     For example, if the database contains ranges like:
     * 
     *       Range 1: 0 - 10,000 miles    
     *       Range 2: 10,001 - 20,000 miles    
     *       Range 3: 20,001 - Infinity    
     *     
     * 
     * 
     * @param yearlyMileage the yearly mileage value to find the range for
     * @return an Optional containing the YearlyMileage entity if a matching range is found,
     *         or empty if no range contains the specified mileage
     */
    @Query("SELECT y FROM YearlyMileage y WHERE y.yearlyMileageFrom <= :yearlyMileage AND y.yearlyMileageTo >= :yearlyMileage")                                                                     
    Optional<YearlyMileage> findByYearlyMileageRange(@Param("yearlyMileage") BigDecimal yearlyMileage);
}
