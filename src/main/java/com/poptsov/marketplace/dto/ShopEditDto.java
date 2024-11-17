package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.validator.ValidEnum;
import jakarta.validation.constraints.*;
import com.poptsov.marketplace.database.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopEditDto {

    @NotBlank(message = "Shop name cannot be Blank")
    private String shopName;

    @NotBlank(message = "Address cannot be Blank")
    private String address;

    @NotNull(message = "Specialization cannot be Blank")
    @ValidEnum(enumClass = Specialization.class, message = "Invalid specialization value")
    private Specialization specialization;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
}