package com.poptsov.marketplace.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto implements CreateDto {


    @NotBlank(message = "Name cannot be Blank")
    private String name;

    @Size(max = 2000, message = "Description must be less than 500 characters")
    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Integer price;

    @Override
    public String getLoggingCreateInfo() {
        return this.getClass().getSimpleName() + " [name=" + name + ", description=" + description + ", price=" + price + "]";
    }
}
