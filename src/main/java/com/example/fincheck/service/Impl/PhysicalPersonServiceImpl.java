package com.example.fincheck.service.Impl;

import com.example.fincheck.dto.PhysicalPersonDto;
import com.example.fincheck.entities.PhysicalPerson;
import com.example.fincheck.repository.IPhysicalPersonRepository;
import com.example.fincheck.service.IPhysicalPersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhysicalPersonServiceImpl implements IPhysicalPersonService {

    private final IPhysicalPersonRepository physicalPersonRepository;

    public PhysicalPersonServiceImpl(IPhysicalPersonRepository physicalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
    }

    /**
     * Get Physical Person By ID
     * @param personID : Physical person ID
     * @return : physical person information
     */
    @Override
    public Optional<PhysicalPerson> findById(Long personID) {
        return physicalPersonRepository.findById(personID);
    }

    /**
     * Add new Physical Person
     * @param person : person information
     * @return : created person location or error code
     */
    @Override
    public ResponseEntity<?> addPhysicalPerson(PhysicalPersonDto person) {
        if (person.getFirstName() == null || person.getLastName() == null || person.getBirthDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Parameters");
        }
        PhysicalPerson savedPerson = physicalPersonRepository.save(
                PhysicalPerson.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .birthDate(person.getBirthDate())
                        .build()
        );
        String location = "/api/physicalPerson/" + savedPerson.getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", location)
                .build();
    }
}
