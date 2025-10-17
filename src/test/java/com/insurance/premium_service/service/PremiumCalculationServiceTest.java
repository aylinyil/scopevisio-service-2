package com.insurance.premium_service.service;

import com.insurance.premium_service.entity.Region;
import com.insurance.premium_service.entity.Vehicle;
import com.insurance.premium_service.entity.YearlyMileage;
import com.insurance.premium_service.model.PremiumRequest;
import com.insurance.premium_service.model.PremiumResponse;
import com.insurance.premium_service.repository.PostCodeRepository;
import com.insurance.premium_service.repository.RegionRepository;
import com.insurance.premium_service.repository.VehicleRepository;
import com.insurance.premium_service.repository.YearlyMileageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PremiumCalculationServiceTest {

    @Mock
    private PostCodeRepository postCodeRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private YearlyMileageRepository yearlyMileageRepository;
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private PremiumCalculationService service;

    @BeforeEach
    void setUp() throws Exception {
        Field baseRateField = PremiumCalculationService.class.getDeclaredField("baseRate");
        baseRateField.setAccessible(true);
        baseRateField.set(service, 100.0d);
    }

    @Test
    void calculate_ok() {
        mockMileageFactor(15000, 1.2);
        mockVehicleFactor("SUV", 1.5);
        mockRegionLookup("12345", "Bayern", 1.1);

        PremiumRequest request = new PremiumRequest(15000, "SUV", "12345");
        PremiumResponse response = service.calculatePremium(request);

        assertNotNull(response);
        assertEquals(198.0, response.getCalculatedPremium(), 1e-6);
    }

    @Test
    void mileage_invalid() {
        when(yearlyMileageRepository.findByYearlyMileageRange(any(BigDecimal.class)))
                .thenReturn(Optional.empty());

        PremiumRequest request = new PremiumRequest(999999999, "SUV", "12345");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.calculatePremium(request));
        assertTrue(ex.getMessage().contains("Invalid yearly mileage"));
    }

    @Test
    void vehicle_invalid() {
        mockMileageFactor(10000, 1.0);
        when(vehicleRepository.findByVehicleType(eq("UNKNOWN"))).thenReturn(Optional.empty());

        PremiumRequest request = new PremiumRequest(10000, "UNKNOWN", "12345");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.calculatePremium(request));
        assertTrue(ex.getMessage().contains("Invalid vehicle type"));
    }

    @Test
    void region_invalid() {
        mockMileageFactor(12000, 1.1);
        mockVehicleFactor("SEDAN", 1.0);

        when(postCodeRepository.findByPostcode(eq("99999"))).thenReturn(Optional.of(new com.insurance.premium_service.entity.PostCode() {{
            // region null -> results in null region name
        }}));

        when(regionRepository.findByRegion(null)).thenReturn(Optional.empty());

        PremiumRequest request = new PremiumRequest(12000, "SEDAN", "99999");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.calculatePremium(request));
        assertTrue(ex.getMessage().contains("Invalid postcode or region"));
    }

    @Test
    void mileageFactor_ok() {
        mockMileageFactor(14000, 1.25);
        Double factor = service.getYearlyMileageFactor(14000);
        assertEquals(1.25, factor, 1e-9);
    }

    @Test
    void postcode_noRegion() {
        Region region = new Region();
        region.setRegion("NRW");
        com.insurance.premium_service.entity.PostCode pc = new com.insurance.premium_service.entity.PostCode();
        
        when(postCodeRepository.findByPostcode(eq("90667"))).thenReturn(Optional.of(pc));

        String name = service.getRegionByPostcode("90667");
        assertNull(name);
    }

    @Test
    void regionFactor_ok() {
        Region region = new Region();
        region.setRegion("Bayern");
        region.setRegionFactor(1.1);
        when(regionRepository.findByRegion(eq("Bayern"))).thenReturn(Optional.of(region));
        Double factor = service.getRegionFactorByRegion("Bayern");
        assertEquals(1.1, factor, 1e-9);
    }

    private void mockMileageFactor(int mileage, double factor) {
        YearlyMileage spyYm = org.mockito.Mockito.spy(new YearlyMileage());
        org.mockito.Mockito.doReturn(factor).when(spyYm).getYearlyMileageFactor();
        when(yearlyMileageRepository.findByYearlyMileageRange(eq(BigDecimal.valueOf(mileage))))
                .thenReturn(Optional.of(spyYm));
    }

    private void mockVehicleFactor(String vehicleType, double factor) {
        Vehicle vehicle = org.mockito.Mockito.spy(new Vehicle());
        org.mockito.Mockito.doReturn(factor).when(vehicle).getVehicleFactor();
        when(vehicleRepository.findByVehicleType(eq(vehicleType))).thenReturn(Optional.of(vehicle));
    }

    private void mockRegionLookup(String postcode, String regionName, double regionFactor) {
        Region region = new Region();
        region.setRegion(regionName);
        region.setRegionFactor(regionFactor);
        when(regionRepository.findByRegion(eq(regionName))).thenReturn(Optional.of(region));

        com.insurance.premium_service.entity.PostCode pc = org.mockito.Mockito.spy(new com.insurance.premium_service.entity.PostCode());
        org.mockito.Mockito.doReturn(region).when(pc).getRegion();
        when(postCodeRepository.findByPostcode(eq(postcode))).thenReturn(Optional.of(pc));
    }
}

    
