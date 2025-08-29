package com.example.fincheck.service;

import com.example.fincheck.dto.PhysicalPersonDto;
import com.example.fincheck.entities.PhysicalPerson;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IPhysicalPersonService {
    Optional<PhysicalPerson> findById(Long personID);
    ResponseEntity<?> addPhysicalPerson(PhysicalPersonDto person);
}
