package com.example.fincheck.service.Impl;

import com.example.fincheck.entities.Beneficiary;
import com.example.fincheck.entities.Company;
import com.example.fincheck.enums.BeneficiaryType;
import com.example.fincheck.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    /**
     * Get all Company Beneficiaries or by type. Beneficiary can be Physical person or not.
     * @param company : company of which we want to get beneficiaries
     * @param type : type of beneficiary : All(beneficiaries), physical persons or effective beneficiaries
     * @return List of beneficiaries
     */
    @Override
    public List<Beneficiary> findAll(Company company, BeneficiaryType type) {

        return (switch (type) {
            case EffectiveBeneficiary ->
                    company.getBeneficiaries().stream().filter(
                                    beneficiary -> beneficiary.getPhysicalPerson() != null && beneficiary.getCapitalPercentage() > 25)
                            .toList();
            case PhysicalPerson ->
                    company.getBeneficiaries().stream().filter(
                            beneficiary -> beneficiary.getPhysicalPerson() != null).toList();
            default ->
                    company.getBeneficiaries();
        });
    }
}
