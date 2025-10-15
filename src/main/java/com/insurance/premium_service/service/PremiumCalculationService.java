package com.insurance.premium_service.service;

import com.insurance.premium_service.entity.*;
import com.insurance.premium_service.model.*;
import com.insurance.premium_service.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PremiumCalculationService {

    private static final Logger log = LoggerFactory.getLogger(PremiumCalculationService.class);

    private final PostCodeRepository postcodeRepository;
    private final RegionRepository regionRepository;
    private final YearlyMileageRepository yearlyMileageRepository;
    private final VehicleRepository vehicleRepository;

    @Value("${premium.calculation.base-rate}")
    private double baseRate;

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

    // Helpers to fetch factors from DB
    public String getRegionByPostcode(String postcode) {
        return postcodeRepository.findByPostcode(postcode)
                .map(p -> p.getRegion() != null ? p.getRegion().getRegion() : null)
                .orElse(null);
    }

    public Double getRegionFactorByRegion(String regionName) {
        return regionRepository.findByRegion(regionName)
                .map(Region::getRegionFactor)
                .orElse(null);
    }

    public Double getYearlyMileageFactor(int yearlyMileage) {
        return yearlyMileageRepository.findByYearlyMileageRange(BigDecimal.valueOf(yearlyMileage))
                .map(YearlyMileage::getYearlyMileageFactor)
                .orElse(null);
    }

    private Double getVehicleFactor(String vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType)
                .map(Vehicle::getVehicleFactor)
                .orElse(null);

    }
}
