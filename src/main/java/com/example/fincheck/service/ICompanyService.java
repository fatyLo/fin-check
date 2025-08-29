package com.example.fincheck.service;

import com.example.fincheck.dto.BeneficiaryDto;
import com.example.fincheck.dto.CompanyDto;
import com.example.fincheck.entities.Beneficiary;
import com.example.fincheck.entities.Company;
import com.example.fincheck.enums.BeneficiaryType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {
    List<Beneficiary> findAll(Company company, BeneficiaryType type);
    ResponseEntity<?> addBeneficiary(Long companyID, BeneficiaryDto beneficiary);
    Optional<Company> findById(Long id);
    ResponseEntity<?> addCompany(CompanyDto company);
}
