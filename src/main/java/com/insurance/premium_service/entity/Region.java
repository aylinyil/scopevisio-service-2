package com.insurance.premium_service.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity representing a geographic region and its associated risk factor.
 * 
 * This entity maps to the 'regions' table in the database and maintains
 * a one-to-many relationship with PostCode entities. Each region has a
 * unique factor that is used as a multiplier in premium calculations.
 * 
 * @author Aylin Yilmazs
 */
@Entity
@Table(name = "regions")
public class Region {
    
    /**
     * Unique identifier for the region record.
     * Generated automatically using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the region (e.g., "Bayern").
     * This field uniquely identifies the region within the system.
     */
    private String region;

    /**
     * The risk factor associated with this region for premium calculations.
     * 
     * This value serves as a multiplier in the premium calculation formula.
     * Higher values indicate higher risk regions, while lower values
     * represent lower risk areas.
     */
    @Column(name = "region_factor")
    private Double regionFactor;

    /**
     * List of postcodes that belong to this region.
     * 
     * This is a one-to-many relationship where one region can contain
     * multiple postcodes. The relationship is lazily loaded for performance
     * optimization and mapped by the 'region' field in the PostCode entity.
     */
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<PostCode> postcodes;

    /**
     * Gets the unique identifier of this region.
     * 
     * @return the region ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of this region.
     * 
     * @return the region name
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the name of this region.
     * 
     * @param region the region name to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the risk factor for this region.
     * 
     * The risk factor is used as a multiplier in premium calculations.
     * A factor of 1.0 represents baseline risk, values above 1.0 indicate
     * higher risk, and values below 1.0 indicate lower risk.
     * 
     * @return the region risk factor
     */
    public Double getRegionFactor() {
        return regionFactor;
    }

    /**
     * Sets the risk factor for this region.
     * 
     * @param regionFactor the region risk factor to set
     */
    public void setRegionFactor(Double regionFactor) {
        this.regionFactor = regionFactor;
    }

    /**
     * Gets the list of postcodes that belong to this region.
     * 
     * Note: This relationship is lazily loaded, so accessing the postcodes
     * may trigger additional database queries if the collection is not
     * properly initialized.
     * 
     * @return the list of postcodes in this region
     */
    public List<PostCode> getPostcodes() {
        return postcodes;
    }
}
