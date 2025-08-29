package com.example.fincheck.service.Impl;

import com.example.fincheck.dto.BeneficiaryDto;
import com.example.fincheck.dto.CompanyDto;
import com.example.fincheck.entities.Beneficiary;
import com.example.fincheck.entities.Company;
import com.example.fincheck.enums.BeneficiaryType;
import com.example.fincheck.repository.IBeneficiaryRepository;
import com.example.fincheck.repository.ICompanyRepository;
import com.example.fincheck.service.ICompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final ICompanyRepository companyRepository;
    private final IBeneficiaryRepository beneficiaryRepository;

    public CompanyServiceImpl(ICompanyRepository companyRepository, IBeneficiaryRepository beneficiaryRepository) {
        this.companyRepository = companyRepository;
        this.beneficiaryRepository = beneficiaryRepository;
    }

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

    /**
     *
     * @param companyID : id of company of which beneficiary will be added
     * @param beneficiary : existing beneficiary to be added to company
     * @return : response entity
     */
    @Override
    public ResponseEntity<?> addBeneficiary(Long companyID, BeneficiaryDto beneficiary) {
        Optional<Company> company = companyRepository.findById(companyID);
        if (company.isPresent()) {
            Optional<Beneficiary> newBeneficiary =  beneficiaryRepository.findById(beneficiary.getId());

            if (newBeneficiary.isPresent() && !company.get().getBeneficiaries().contains(newBeneficiary.get())) {
                int totalCapital = company.get().getBeneficiaries().stream()
                        .map(Beneficiary::getCapitalPercentage)
                        .reduce(0, Integer::sum) + beneficiary.getCapitalPercentage();

                if (totalCapital <= 100) {
                    company.get().getBeneficiaries().add(newBeneficiary.get());
                    companyRepository.save(company.get());
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
                else
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Add Beneficiary not allowed : Sum of beneficiaries' capital percentage greater than 100");
            }
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Beneficiary not found");
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Company not found");
    }

    /**
     * Find Company By ID
     * @param id : company ID
     * @return : company or error code
     */
    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    /**
     * Add a new Company
     * @param company : company values
     * @return : created company location or error code
     */
    @Override
    public ResponseEntity<?> addCompany(CompanyDto company) {
        if (company.getName() != null && !company.getName().isEmpty()) {
            Company newCompany = companyRepository.save(Company.builder()
                    .name(company.getName())
                    .build());
            String location = "/api/company/" + newCompany.getId();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", location)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Incorrect Parameters");
    }
}
