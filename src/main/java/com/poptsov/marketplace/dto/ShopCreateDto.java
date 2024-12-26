package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Specialization;
import com.poptsov.marketplace.validator.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopCreateDto implements CreateDto {

    @NotBlank(message = "Shop name cannot be Blank")
    private String name;

    @NotBlank(message = "Address cannot be Blank")
    private String address;

    @NotNull(message = "Specialization cannot be Null")
    @ValidEnum(enumClass = Specialization.class, message = "Invalid specialization value")
    private Specialization specialization;

    @Size(max = 2000, message = "Description must be less than 500 characters")
    private String description;

    @Override
    public String getLoggingCreateInfo() {
        return this.getClass().getSimpleName() + ": " + name;
    }

}
