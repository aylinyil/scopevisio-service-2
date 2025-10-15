package com.insurance.premium_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.premium_service.entity.PostCode;

/**
 * Repository interface for managing PostCode entities.
 *     
 * This repository provides data access operations for postcode records,
 * including the ability to find postcodes by their string value. It extends
 * JpaRepository to inherit standard CRUD operations and query methods.
 * 
 *     The repository is primarily used during premium calculations to look up
 * the region associated with a given postcode, which is then used to determine
 * the appropriate regional risk factor.
 * 
 * @author Aylin Yilmaz
 */
@Repository
public interface PostCodeRepository extends JpaRepository<PostCode, Long> {
    
    /**
     * Finds a postcode record by its postcode string value.
     *     
     * This method performs a case-sensitive search for the exact postcode
     * string in the database. The postcode string should match exactly
     * as stored in the database, including any formatting (spaces, dashes, etc.).
     * 
     * @param postcode the postcode string to search for (e.g., "12345")
     * @return an Optional containing the PostCode entity if found, or empty if not found
     */
    Optional<PostCode> findByPostcode(String postcode);
}

