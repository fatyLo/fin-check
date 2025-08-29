package com.example.fincheck.repository;

import com.example.fincheck.entities.PhysicalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhysicalPersonRepository extends JpaRepository<PhysicalPerson, Long> {
}
