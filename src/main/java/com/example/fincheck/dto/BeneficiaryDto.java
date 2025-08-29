package com.example.fincheck.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDto {
    private Long id;
    private int capitalPercentage;
}
