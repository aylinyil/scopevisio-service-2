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

@Entity
@Table(name = "regions")
public class Region {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;

    @Column(name = "region_factor")
    private Double regionFactor;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<PostCode> postcodes;

    public Long getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getRegionFactor() {
        return regionFactor;
    }

    public void setRegionFactor(Double regionFactor) {
        this.regionFactor = regionFactor;
    }

    public List<PostCode> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(List<PostCode> postcodes) {
        this.postcodes = postcodes;
    }
}
