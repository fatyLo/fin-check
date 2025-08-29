package com.example.fincheck.controller;

import com.example.fincheck.dto.PhysicalPersonDto;
import com.example.fincheck.service.IPhysicalPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/physicalPerson")
public class PhysicalPersonController {

    private final IPhysicalPersonService physicalPersonService;

    public PhysicalPersonController(IPhysicalPersonService physicalPersonService) {
        this.physicalPersonService = physicalPersonService;
    }

    @Operation(summary = "Get Physical Person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Physical Person Successfully added"),
            @ApiResponse(responseCode = "404", description = "Physical Person not found"),
    })
    @GetMapping("{personID}")
    public ResponseEntity<?> getPhysicalPerson(@PathVariable(name = "personID") Long personID) {
        return physicalPersonService.findById(personID).map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Add Physical Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Physical Person Successfully added"),
            @ApiResponse(responseCode = "400", description = "Incorrect Parameters")
    })
    @PostMapping
    public ResponseEntity<?>  addPhysicalPerson(@RequestBody PhysicalPersonDto person) {
        return physicalPersonService.addPhysicalPerson(person);
    }
}
