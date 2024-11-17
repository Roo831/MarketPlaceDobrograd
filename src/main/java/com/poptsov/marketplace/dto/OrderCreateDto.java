package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.Status;
import com.poptsov.marketplace.database.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {

    @NotNull(message = "CreatedAt is required")
    private Date createdAt;

    @NotNull(message = "Status is required")
    private Status status;

    @NotBlank(message = "Name is required")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Integer price;

    @NotNull(message = "User  is required")
    private User user;

    @NotNull(message = "Shop is required")
    private Shop shop;
}
