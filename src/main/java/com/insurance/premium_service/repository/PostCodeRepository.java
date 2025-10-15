package com.insurance.premium_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.insurance.premium_service.entity.PostCode;

@Repository
public interface PostCodeRepository extends JpaRepository<PostCode, Long> {
    Optional<PostCode> findByPostcode(String postcode);
}

