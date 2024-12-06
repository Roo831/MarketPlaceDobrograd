package com.poptsov.marketplace.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchAdminDto implements UpdateDto {

    @NotNull(message = "admin cannot be null")
    private boolean admin;

    @Override
    public String getLoggingUpdateInfo() {
        return this.getClass().getSimpleName() + " [admin=" + admin + "]";
    }
}
