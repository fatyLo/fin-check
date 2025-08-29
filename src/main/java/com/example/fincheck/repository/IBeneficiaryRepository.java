package com.example.fincheck.repository;

import com.example.fincheck.entities.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
}
