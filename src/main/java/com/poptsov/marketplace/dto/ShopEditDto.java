package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Specialization;
import com.poptsov.marketplace.validator.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopEditDto implements UpdateDto {

    @NotBlank(message = "Shop name cannot be Blank")
    private String name;

    @NotBlank(message = "Address cannot be Blank")
    private String address;

    @NotNull(message = "Specialization cannot be Null")
    @ValidEnum(enumClass = Specialization.class, message = "Invalid specialization value")
    private Specialization specialization;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Override
    public String getLoggingUpdateInfo() {
        return this.getClass().getSimpleName() + ": " + name;
    }
}