package com.insurance.premium_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity representing yearly mileage ranges and their associated factors.
 *     
 * This entity maps to the 'yearly_mileage' table in the database and defines
 * mileage ranges with corresponding risk factors used in premium calculations.
 * Higher mileage typically correlates with increased accident risk, which is
 * reflected in the risk factor values.
 * 
 * @author Aylin Yilmaz
 */
@Entity
@Table(name = "yearly_mileage")
public class YearlyMileage {
    
    /**
     * Unique identifier for the yearly mileage range record.
     * Generated automatically using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The lower bound of the mileage range (inclusive).
     *     
     * This value represents the minimum yearly mileage that falls
     * within this range category. For example, if the range covers
     * 10,000-15,000 miles, this field would contain 10,000.
     */
    @Column(name = "yearly_mileage_from")
    private BigDecimal yearlyMileageFrom;

    /**
     * The upper bound of the mileage range (inclusive).
     *     
     * This value represents the maximum yearly mileage that falls
     * within this range category. For example, if the range covers
     * 10,000-15,000 miles, this field would contain 15,000.
     */
    @Column(name = "yearly_mileage_to")
    private BigDecimal yearlyMileageTo;

    /**
     * The risk factor associated with this mileage range for premium calculations.
     *     
     * This value serves as a multiplier in the premium calculation formula.
     * Higher values indicate higher risk mileage ranges (more driving exposure),
     * while lower values represent lower risk ranges (less driving exposure).
     * A factor of 1.0 represents baseline risk.
     */
    @Column(name = "yearly_mileage_factor")
    private Double yearlyMileageFactor;

    /**
     * Gets the unique identifier of this mileage range.
     * 
     * @return the mileage range ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the lower bound of the mileage range.
     * 
     * @return the minimum yearly mileage for this range (inclusive)
     */
    public BigDecimal getYearlyMileageFrom() {
        return yearlyMileageFrom;
    }

    /**
     * Gets the upper bound of the mileage range.
     * 
     * @return the maximum yearly mileage for this range (inclusive)
     */
    public BigDecimal getYearlyMileageTo() {
        return yearlyMileageTo;
    }

    /**
     * Gets the risk factor for this mileage range.
     *     
     * The mileage factor is used as a multiplier in premium calculations.
     * A factor of 1.0 represents baseline risk, values above 1.0 indicate
     * higher risk mileage ranges, and values below 1.0 indicate lower risk ranges.
     * 
     * @return the yearly mileage risk factor
     */
    public Double getYearlyMileageFactor() {
        return yearlyMileageFactor;
    }
}
