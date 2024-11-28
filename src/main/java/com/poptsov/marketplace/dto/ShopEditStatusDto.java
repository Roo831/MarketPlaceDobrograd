package com.poptsov.marketplace.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopEditStatusDto implements UpdateDto {

    @NotNull(message = "active cannot be null")
    private boolean active;

    @Override
    public String getLoggingUpdateInfo() {
        return this.getClass().getSimpleName() + " [active=" + active + "]";
    }
}
