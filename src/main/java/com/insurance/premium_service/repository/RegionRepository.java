package com.insurance.premium_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.premium_service.entity.Region;

/**
 * Repository interface for managing Region entities.
 *     
 * This repository provides data access operations for region records,
 * including the ability to find regions by their name. It extends
 * JpaRepository to inherit standard CRUD operations and query methods.
 * 
 *     The repository is used during premium calculations to retrieve
 * region-specific risk factors that are applied as multipliers in the
 * premium calculation formula.
 * 
 * @author Aylin Yilmaz
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    
    /**
     * Finds a region record by its region name.
     *     
     * This method performs a case-sensitive search for the exact region
     * name in the database. The region name should match exactly as stored
     * in the database.
     * 
     * @param region the region name to search for (e.g., "Bayern")
     * @return an Optional containing the Region entity if found, or empty if not found
     */
    Optional<Region> findByRegion(String region);
    
}