package com.test.toolmanagement.repository;

import com.test.toolmanagement.entity.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {
}
