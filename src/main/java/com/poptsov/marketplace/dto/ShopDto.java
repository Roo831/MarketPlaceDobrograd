package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Specialization;
import com.poptsov.marketplace.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {

    @NotNull(message = "ID is required")
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @Min(value = 0, message = "Rating must be greater than or equal to 0")
    private Integer rating;

    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    @NotNull(message = "CreatedAt is required")
    private Date createdAt;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "User  ID is required")
    private Integer userId;
}
