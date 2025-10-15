package com.insurance.premium_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transfer object representing a premium calculation request.
 *     
 * This class encapsulates all the necessary information required to calculate
 * an insurance premium, including vehicle information, driving patterns, and
 * geographic location. It serves as the input model for the premium calculation
 * REST API endpoint.
 * 
 *     Example JSON representation:
 * 
 * {
 *   "yearlyMileage": 15000,
 *   "vehicleType": "SUV",
 *   "postcode": "12345"
 * }
 * 
 * @author Aylin Yilmaz
 */
public class PremiumRequest {

    /**
     * The yearly mileage driven by the vehicle owner.
     *     
     * This value represents the estimated number of miles or kilometers
     * the vehicle will be driven per year. Higher mileage typically
     * correlates with increased accident risk and higher premiums.
     */
    @JsonProperty("yearlyMileage")
    private int yearlyMileage;

    /**
     * The postcode of the vehicle owner's primary location.
     *     
     * This is used to determine the geographic region for risk assessment.
     * Different regions may have varying crime rates, weather conditions,
     * and accident frequencies that affect premium calculations.
     */
    @JsonProperty("postcode")
    private String postcode;

    /**
     * The type or category of the vehicle being insured.
     *     
     * Different vehicle types have different risk profiles based on
     * factors such as safety ratings, repair costs, and theft rates.
     */
    @JsonProperty("vehicleType")
    private String vehicleType;



    /**
     * Constructs a new PremiumRequest with the specified parameters.
     * 
     * @param yearlyMileage the estimated yearly mileage
     * @param vehicleType the type of vehicle being insured
     * @param postcode the postcode of the vehicle owner's location
     */
    public PremiumRequest(int yearlyMileage, String vehicleType, String postcode) {
        this.yearlyMileage = yearlyMileage;
        this.vehicleType = vehicleType;
        this.postcode = postcode;
    }

    /**
     * Gets the yearly mileage.
     * 
     * @return the estimated yearly mileage
     */
    public int getYearlyMileage() {
        return yearlyMileage;
    }

    /**
     * Gets the vehicle type.
     * 
     * @return the type of vehicle being insured
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Gets the postcode.
     * 
     * @return the postcode of the vehicle owner's location
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Returns a string representation of this premium request.
     *     
     * @return a string containing all field values
     */
    @Override
    public String toString() {
        return "PremiumRequest{" +
                ", yearlyMileage=" + yearlyMileage +
                ", postcode='" + postcode + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}
