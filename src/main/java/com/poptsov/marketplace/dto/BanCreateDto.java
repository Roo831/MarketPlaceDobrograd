package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanCreateDto implements CreateDto {

    @NotBlank(message = "Description of ban cannot be Blank")
    private String descriptionOfBan;

    @Override
    public String getLoggingCreateInfo() {
        return this.getClass().getSimpleName() + ": " + descriptionOfBan;
    }
}
