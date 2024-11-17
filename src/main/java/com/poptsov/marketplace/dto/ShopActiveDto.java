package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopActiveDto {

    @NotNull(message = "Specialization cannot be Null")
    private boolean isActive;

}
