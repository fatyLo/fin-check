package com.example.fincheck;

import com.example.fincheck.entities.Beneficiary;
import com.example.fincheck.entities.Company;
import com.example.fincheck.entities.PhysicalPerson;
import com.example.fincheck.repository.IBeneficiaryRepository;
import com.example.fincheck.repository.ICompanyRepository;
import com.example.fincheck.repository.IPhysicalPersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class FinCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinCheckApplication.class, args);
    }

    /**
     * Init some data in H2 database. Data must be reset on application start
     */
    @Bean
    CommandLineRunner init(IBeneficiaryRepository beneficiaryRepository,
                           ICompanyRepository companyRepository, IPhysicalPersonRepository physicalPersonRepository) {
        return args -> {
            PhysicalPerson p1 = physicalPersonRepository.save(PhysicalPerson.builder()
                    .firstName("Faty")
                    .lastName("LO")
                    .birthDate(new Date())
                    .build());
            PhysicalPerson p2 = physicalPersonRepository.save(PhysicalPerson.builder()
                    .firstName("Julie")
                    .lastName("DUPONT")
                    .birthDate(new Date())
                    .build());
            Beneficiary b1 = beneficiaryRepository.save(Beneficiary.builder()
                    .name("Beneficiary 1")
                    .capitalPercentage(27)
                    .build());
            Beneficiary b2 = beneficiaryRepository.save(Beneficiary.builder()
                    .name("Beneficiary 2")
                    .capitalPercentage(36)
                    .physicalPerson(p2)
                    .build());
            Beneficiary b3 = beneficiaryRepository.save(Beneficiary.builder()
                    .name("Beneficiary 3")
                    .capitalPercentage(23)
                    .physicalPerson(p1)
                    .build());
            List<Beneficiary> beneficiaries = new ArrayList<>();
            beneficiaries.add(b1);
            beneficiaries.add(b2);
            beneficiaries.add(b3);
            companyRepository.save(Company.builder()
                    .name("Company 1")
                    .beneficiaries(List.of(b1))
                    .build());
            companyRepository.save(Company.builder()
                    .name("Company 2")
                    .beneficiaries(beneficiaries)
                    .build());
        };
    }

}
