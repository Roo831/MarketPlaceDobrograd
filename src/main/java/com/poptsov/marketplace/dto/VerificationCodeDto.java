package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationCodeDto {
    @NotBlank
    private String email;

    @NotBlank
    private String code;
}
