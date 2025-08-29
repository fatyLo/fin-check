package com.example.fincheck.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalPersonDto {
    private String firstName;
    private String lastName;
    private Date birthDate;
}
