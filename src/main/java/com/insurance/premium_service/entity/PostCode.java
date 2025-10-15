package com.insurance.premium_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity representing a postcode and its associated region.
 * 
 * This entity maps to the 'postcodes' table in the database and establishes
 * a many-to-one relationship with the Region entity. Each postcode belongs
 * to exactly one region, which is used for determining regional factors
 * in premium calculations.
 * 
 * @author Aylin Yilmaz
 */
@Entity
@Table(name = "postcodes")
public class PostCode {

    /**
     * Unique identifier for the postcode record.
     * Generated automatically using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    /**
     * The postcode value (e.g., "12345").
     * This field stores the actual postcode string used for lookups.
     */
    private String postcode;

    /**
     * The region associated with this postcode.
     * 
     * This is a many-to-one relationship where multiple postcodes
     * can belong to the same region.s
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
    
    /**
     * Gets the postcode value.
     * 
     * @return the postcode string
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the postcode value.
     * 
     * @param postcode the postcode string to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Gets the region associated with this postcode.
     * 
     * Note: This relationship is lazily loaded, so accessing the region
     * properties may trigger additional database queries if the entity
     * is not properly initialized.
     * 
     * @return the associated Region entity, or null if not set
     */
    public Region getRegion() {
        return region;
    }
}
