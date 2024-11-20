package com.poptsov.marketplace.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopEditStatusDto {

    @NotNull(message = "active cannot be null")
    private boolean active;

}
