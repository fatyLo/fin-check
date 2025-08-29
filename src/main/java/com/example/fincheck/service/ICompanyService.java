package com.example.fincheck.service;

import com.example.fincheck.entities.Beneficiary;
import com.example.fincheck.entities.Company;
import com.example.fincheck.enums.BeneficiaryType;

import java.util.List;

public interface ICompanyService {
    List<Beneficiary> findAll(Company company, BeneficiaryType type);
}
