package com.poptsov.marketplace.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwitchAdminDto {

    @NotNull(message = "admin cannot be null")
    private boolean admin;
}
