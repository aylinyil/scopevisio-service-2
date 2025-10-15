package com.insurance.premium_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "yearly_mileage")
public class YearlyMileage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "yearly_mileage_from")
    private BigDecimal yearlyMileageFrom;

    @Column(name = "yearly_mileage_to")
    private BigDecimal yearlyMileageTo;

    @Column(name = "yearly_mileage_factor")
    private Double yearlyMileageFactor;

    public Long getId() {
        return id;
    }

    public BigDecimal getYearlyMileageFrom() {
        return yearlyMileageFrom;
    }

    public BigDecimal getYearlyMileageTo() {
        return yearlyMileageTo;
    }

    public Double getYearlyMileageFactor() {
        return yearlyMileageFactor;
    }
}
