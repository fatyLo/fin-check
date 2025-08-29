package com.example.fincheck.controller;

import com.example.fincheck.entities.Beneficiary;
import com.example.fincheck.entities.Company;
import com.example.fincheck.enums.BeneficiaryType;
import com.example.fincheck.repository.ICompanyRepository;
import com.example.fincheck.service.ICompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final ICompanyRepository companyRepository;
    private final ICompanyService companyService;

    public CompanyController(ICompanyRepository companyRepository, ICompanyService companyService) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }


    @Operation(summary = "Get Company Beneficiaries List by type : all beneficiaries, physical persons only or effective beneficiaries only")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiaries List with percentages of ownership"),
            @ApiResponse(responseCode = "204", description = "No beneficiary found for company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/beneficiaries/{companyID}/{type}")
    public ResponseEntity<?> getBeneficiaries(@PathVariable(name = "companyID") Long companyID,
                                              @PathVariable(name = "type") BeneficiaryType type) {

        Company company = companyRepository.findById(companyID).orElse(null);

        if (company == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Company not found");
        }
        List<Beneficiary> results =  companyService.findAll(company, type);

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);

    }
}
