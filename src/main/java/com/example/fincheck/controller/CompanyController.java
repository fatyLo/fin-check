package com.example.fincheck.controller;

import com.example.fincheck.dto.BeneficiaryDto;
import com.example.fincheck.dto.CompanyDto;
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
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Add Beneficiary to company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beneficiary Successfully added to company"),
            @ApiResponse(responseCode = "404", description = "Company or Beneficiary not found"),
            @ApiResponse(responseCode = "400", description = "Add Beneficiary not allowed : Sum of beneficiaries' capital percentage greater than 100")
    })
    @PostMapping("/addBeneficiary/{companyID}")
    public ResponseEntity<?> addBeneficiary(@PathVariable(name = "companyID") Long companyID,
                                            @RequestBody BeneficiaryDto beneficiary) {
        return companyService.addBeneficiary(companyID, beneficiary);
    }

    @Operation(summary = "Get Company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
    })
    @GetMapping("{companyID}")
    public ResponseEntity<?> getCompany(@PathVariable(name = "companyID") Long companyID) {
        return companyService.findById(companyID).map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @Operation(summary = "Add Company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company Successfully added"),
            @ApiResponse(responseCode = "400", description = "Incorrect Parameters")
    })
    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyDto company) {
        return companyService.addCompany(company);
    }

}
