package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Specialization;
import com.poptsov.marketplace.validator.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopEditDto implements UpdateDto {

    @NotBlank(message = "Shop name cannot be Blank")
    private String name;

    @NotBlank(message = "Address cannot be Blank")
    private String address;

    @NotNull(message = "Specialization cannot be Null")
    @ValidEnum(enumClass = Specialization.class, message = "Invalid specialization value")
    private Specialization specialization;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @Override
    public String getLoggingUpdateInfo() {
        return this.getClass().getSimpleName() + ": " + name;
    }
}