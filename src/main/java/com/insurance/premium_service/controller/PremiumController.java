package com.insurance.premium_service.controller;

import com.insurance.premium_service.model.PremiumRequest;
import com.insurance.premium_service.model.PremiumResponse;
import com.insurance.premium_service.service.PremiumCalculationService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Controller for handling insurance premium calculation requests.
 *
 * This controller provides HTTP endpoints for calculating insurance premiums
 * based on input parameters such as vehicle type, yearly mileage, and postcode.
 * All endpoints return JSON responses and expect JSON request bodies where applicable.
 * 
 * Base URL: {/api/premium}
 * 
 * @author Aylin Yilmaz
 */
@RestController
@RequestMapping("/api/premium")
public class PremiumController {

    private static final Logger log = LoggerFactory.getLogger(PremiumController.class);

    private final PremiumCalculationService premiumCalculationService;

    /**
     * Constructs a new PremiumController with the specified premium calculation service.
     * 
     * @param premiumCalculationService the service responsible for premium calculations,
     *                                must not be null
     */
    public PremiumController(PremiumCalculationService premiumCalculationService) {
        this.premiumCalculationService = premiumCalculationService;
    }

    /**
     * Calculates insurance premium based on the provided request parameters.
     * 
     * This endpoint accepts a POST request with a JSON body containing vehicle information,
     * yearly mileage, and postcode, then returns the calculated premium amount.
     * 
     * Example request body:
     *
     * {
     *   "vehicleType": "SUV",
     *   "yearlyMileage": 15000,
     *   "postcode": "12345"
     * }
     * 
     * 
     * Example response:
     *
     * {"calculatedPremium": 750.50}
     * 
     * @param request the premium calculation request containing vehicle type,
     *               yearly mileage, and postcode
     * @return PremiumResponse containing the calculated premium amount
     * @throws IllegalArgumentException if any of the request parameters are invalid
     *                                 (e.g., unknown vehicle type, invalid postcode)
     */
    @PostMapping("/calculate")
    public PremiumResponse calculate(@RequestBody PremiumRequest request) {
        log.info("Calculation of premium for request: {}", request);
        
        PremiumResponse response = premiumCalculationService.calculatePremium(request);

        log.info("Premium calculation completed successfully: {}", response.getCalculatedPremium());

        return response;
    }
}

