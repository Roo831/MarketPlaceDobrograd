package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.*;
import com.poptsov.marketplace.database.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopEditDto {

    @NotBlank(message = "Shop name is required")
    private String shopName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
}