package com.insurance.premium_service.model;

/**
 * Data transfer object representing the result of a premium calculation.
 *     
 * This class encapsulates the calculated insurance premium amount returned
 * by the premium calculation service. It serves as the response model for
 * the premium calculation REST API endpoint.
 * 
 *     Example JSON representation:
 * 
 * {
 *   "calculatedPremium": 750.50
 * }
 * 
 * 
 * @author Aylin Yilmaz
 */
public class PremiumResponse {

    /**
     * The calculated insurance premium amount.
     *     
     * This value represents the final premium amount calculated based on
     * the input parameters including base rate, vehicle factor, mileage factor,
     * and region factor. The amount is typically expressed in the currency
     * configured for the insurance system.
     */
    private double calculatedPremium;

    /**
     * Default constructor for JSON serialization.
     * Creates an empty PremiumResponse instance with zero premium.
     */
    public PremiumResponse() {}

    /**
     * Constructs a new PremiumResponse with the specified premium amount.
     * 
     * @param calculatedPremium the calculated premium amount
     */
    public PremiumResponse(double calculatedPremium) {
        this.calculatedPremium = calculatedPremium;
    }

    /**
     * Gets the calculated premium amount.
     * 
     * @return the calculated insurance premium
     */
    public double getCalculatedPremium() {
        return calculatedPremium;
    }

    /**
     * Sets the calculated premium amount.
     * 
     * @param calculatedPremium the calculated premium amount to set
     */
    public void setCalculatedPremium(double calculatedPremium) {
        this.calculatedPremium = calculatedPremium;
    }

    /**
     * Returns a string representation of this premium response.
     *     
     * This method is useful for logging and debugging purposes.
     * 
     * @return a string containing the calculated premium value
     */
    @Override
    public String toString() {
        return "PremiumResponse{" +
                "calculatedPremium=" + calculatedPremium +
                '}';
    }
}

