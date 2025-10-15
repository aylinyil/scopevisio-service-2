package com.insurance.premium_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PremiumRequest {

    @JsonProperty("yearlyMileage")
    private int yearlyMileage;

    @JsonProperty("postcode")
    private String postcode;

    @JsonProperty("vehicleType")
    private String vehicleType;

    public PremiumRequest() {
    }

    public PremiumRequest(int yearlyMileage, String vehicleType, String postcode) {
        this.yearlyMileage = yearlyMileage;
        this.vehicleType = vehicleType;
        this.postcode = postcode;
    }

    public int getYearlyMileage() {
        return yearlyMileage;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getPostcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return "PremiumRequest{" +
                ", yearlyMileage=" + yearlyMileage +
                ", postcode='" + postcode + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
