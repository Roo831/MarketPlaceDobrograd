package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.Status;
import jakarta.validation.constraints.*;
import com.poptsov.marketplace.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @NotNull(message = "Order ID is required")
    private Integer orderId;

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

    @NotNull(message = "User  ID is required")
    private Integer userId;

    @NotNull(message = "Shop ID is required")
    private Integer shopId;
}