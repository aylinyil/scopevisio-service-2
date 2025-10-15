package com.insurance.premium_service.model;

public class PremiumResponse {

    private double calculatedPremium;

    public PremiumResponse() {}

    public PremiumResponse(double calculatedPremium) {
        this.calculatedPremium = calculatedPremium;
    }

    public double getCalculatedPremium() {
        return calculatedPremium;
    }

    public void setCalculatedPremium(double calculatedPremium) {
        this.calculatedPremium = calculatedPremium;
    }

    @Override
    public String toString() {
        return "PremiumResponse{" +
                "calculatedPremium=" + calculatedPremium +
                '}';
    }
}

