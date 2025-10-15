package com.insurance.premium_service.service;

import com.insurance.premium_service.entity.*;
import com.insurance.premium_service.model.*;
import com.insurance.premium_service.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service class responsible for calculating insurance premiums.
 * 
 * This service implements the core business logic for premium calculation by combining
 * multiple risk factors including vehicle type, yearly mileage, and geographic region.
 * The calculation follows the formula:
 * 
 * Premium = Base Rate × Vehicle Factor × Mileage Factor × Region Factor
 * 
 * All factors are retrieved from the database based on the input parameters
 * provided in the premium request.
 * 
 * @author Aylin Yilmaz
 */
@Service
public class PremiumCalculationService {

    private static final Logger log = LoggerFactory.getLogger(PremiumCalculationService.class);

    private final PostCodeRepository postcodeRepository;
    private final RegionRepository regionRepository;
    private final YearlyMileageRepository yearlyMileageRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * Base rate for premium calculation, injected from application properties.
     * This value serves as the starting point for all premium calculations.
     */
    @Value("${premium.calculation.base-rate}")
    private double baseRate;

    /**
     * Constructs a new PremiumCalculationService with the required repositories.
     * 
     * @param postcodeRepository repository for postcode lookups, must not be null
     * @param regionRepository repository for region data access, must not be null
     * @param yearlyMileageRepository repository for mileage factor lookups, must not be null
     * @param vehicleRepository repository for vehicle factor lookups, must not be null
     */
    public PremiumCalculationService(
            PostCodeRepository postcodeRepository, 
            RegionRepository regionRepository,
            YearlyMileageRepository yearlyMileageRepository, 
            VehicleRepository vehicleRepository) {

        this.postcodeRepository = postcodeRepository;
        this.regionRepository = regionRepository;
        this.yearlyMileageRepository = yearlyMileageRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Calculates the insurance premium based on the provided request parameters.
     * 
     * This method performs the following steps:
     * 
     *   1. Validates and retrieves the yearly mileage factor  
     *   2. Validates and retrieves the vehicle type factor  
     *   3. Looks up the region based on the provided postcode  
     *   4. Retrieves the region factor 
     *   5. Calculates the final premium using all factors  
     * 
     * 
     * @param request the premium calculation request containing vehicle type,
     *               yearly mileage, and postcode
     * @return PremiumResponse containing the calculated premium amount
     * @throws IllegalArgumentException if any of the input parameters are invalid:
     *                                    
     *                                    Invalid yearly mileage (no matching range found)  
     *                                    Invalid vehicle type (not found in database)  
     *                                    Invalid postcode (not found or no associated region)  
     *                                 
     */
    public PremiumResponse calculatePremium(PremiumRequest request) {
        
        log.info("Starting premium calculation for request: {}", request);

        Double yearlyMileageFactor = getYearlyMileageFactor(request.getYearlyMileage());

        if (yearlyMileageFactor == null) {
            log.error("No yearly mileage factor found for mileage: {}", request.getYearlyMileage());
            throw new IllegalArgumentException("Invalid yearly mileage: " + request.getYearlyMileage());
        }

        Double vehicleFactor = getVehicleFactor(request.getVehicleType());
        if (vehicleFactor == null) {
            log.error("No vehicle factor found for vehicle type: {}", request.getVehicleType());
            throw new IllegalArgumentException("Invalid vehicle type: " + request.getVehicleType());
        }

        // 1. Get region name by postcode
        String regionName = getRegionByPostcode(request.getPostcode());

        // 2. Get region factor based on region name
        Double regionFactor = getRegionFactorByRegion(regionName);

        if (regionFactor == null) {
            log.error("No region factor found for region '{}' (postcode={})", regionName, request.getPostcode());
                    request.getPostcode();
            throw new IllegalArgumentException("Invalid postcode or region: " + request.getPostcode());
        }

        log.debug("Factors used → baseRate={}, yearlyMileageFactor={}, vehicleFactor={}, regionFactor={}",
                baseRate, yearlyMileageFactor, vehicleFactor, regionFactor);

        // Calculate total premium based on factors
        double total = baseRate * yearlyMileageFactor * vehicleFactor * regionFactor;
        log.info("Premium calculation successful: total={}", total);

        PremiumResponse response = new PremiumResponse(total);
        log.debug("Response object: " + response);

        return response;
    }

    /**
     * Retrieves the region name associated with the specified postcode.
     * 
     * This method performs a database lookup to find the postcode record
     * and returns the associated region name.
     * 
     * @param postcode the postcode to look up, must not be null
     * @return the region name associated with the postcode, or null if not found
     */
    public String getRegionByPostcode(String postcode) {
        return postcodeRepository.findByPostcode(postcode)
                .map(p -> p.getRegion() != null ? p.getRegion().getRegion() : null)
                .orElse(null);
    }

    /**
     * Retrieves the region factor for premium calculation based on the region name.
     * 
     * The region factor represents the risk multiplier associated with a specific
     * geographic region.
     * 
     * @param regionName the name of the region to look up, must not be null
     * @return the region factor for premium calculation, or null if region not found
     */
    public Double getRegionFactorByRegion(String regionName) {
        return regionRepository.findByRegion(regionName)
                .map(Region::getRegionFactor)
                .orElse(null);
    }

    /**
     * Retrieves the yearly mileage factor based on the provided mileage value.
     * 
     * This method finds the appropriate mileage range that contains the specified
     * yearly mileage and returns the associated factor for premium calculation.
     * 
     * @param yearlyMileage the yearly mileage in kilometers or miles
     * @return the mileage factor for premium calculation, or null if no matching range found
     */
    public Double getYearlyMileageFactor(int yearlyMileage) {
        return yearlyMileageRepository.findByYearlyMileageRange(BigDecimal.valueOf(yearlyMileage))
                .map(YearlyMileage::getYearlyMileageFactor)
                .orElse(null);
    }

    /**
     * Retrieves the vehicle factor based on the vehicle type.
     * 
     * The vehicle factor represents the risk multiplier associated with different
     * types of vehicles.
     * 
     * @param vehicleType the type of vehicle (e.g., "sedan", "SUV", "sports_car")
     * @return the vehicle factor for premium calculation, or null if vehicle type not found
     */
    private Double getVehicleFactor(String vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType)
                .map(Vehicle::getVehicleFactor)
                .orElse(null);

    }
}
