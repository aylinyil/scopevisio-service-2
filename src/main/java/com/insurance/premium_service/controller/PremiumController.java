package com.insurance.premium_service.controller;

import com.insurance.premium_service.model.PremiumRequest;
import com.insurance.premium_service.model.PremiumResponse;
import com.insurance.premium_service.service.PremiumCalculationService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/premium")
public class PremiumController {

    private static final Logger log = LoggerFactory.getLogger(PremiumController.class);

    private final PremiumCalculationService premiumCalculationService;

    public PremiumController(PremiumCalculationService premiumCalculationService) {
        this.premiumCalculationService = premiumCalculationService;
    }

    @PostMapping("/calculate")
    public PremiumResponse calculate(@RequestBody PremiumRequest request) {
        log.info("Calculation of premium for request: {}", request);
        
        PremiumResponse response = premiumCalculationService.calculatePremium(request);

        log.info("Premium calculation completed successfully: {}", response.getCalculatedPremium());

        return response;
    }
}

