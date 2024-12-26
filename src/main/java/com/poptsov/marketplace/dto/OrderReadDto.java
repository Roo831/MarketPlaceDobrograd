package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderReadDto implements ReadDto {

    @NotNull(message = "Order ID cannot be Blank")
    private Integer orderId;

    @NotNull(message = "CreatedAt cannot be Null")
    private Date createdAt;

    @NotNull(message = "Status cannot be Null")
    private Status status;

    @NotBlank(message = "Name cannot be Blank")
    private String name;

    @Size(max = 2000, message = "Description must be less than 500 characters")
    private String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Integer price;

    @NotNull(message = "User ID cannot be Null")
    private Integer userId;

    @NotNull(message = "Shop ID cannot be Null")
    private Integer shopId;
}