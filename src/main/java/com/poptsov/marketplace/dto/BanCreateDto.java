package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanCreateDto {

    @NotBlank(message = "Description of ban cannot be Blank")
    private String descriptionOfBan;

}
