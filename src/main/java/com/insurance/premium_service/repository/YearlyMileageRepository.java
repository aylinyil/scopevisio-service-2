package com.insurance.premium_service.repository;
import java.math.BigDecimal;
import com.insurance.premium_service.entity.YearlyMileage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface YearlyMileageRepository extends JpaRepository<YearlyMileage, Long>{
    @Query("SELECT y FROM YearlyMileage y WHERE y.yearlyMileageFrom <= :yearlyMileage AND y.yearlyMileageTo >= :yearlyMileage")                                                                     
    Optional<YearlyMileage> findByYearlyMileageRange(@Param("yearlyMileage") BigDecimal yearlyMileage);
}
