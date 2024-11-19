package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopReadDto {

    @NotNull(message = "ID cannot be Null")
    private Integer id;

    @NotBlank(message = "Name cannot be Blank")
    private String name;

    @NotBlank(message = "Address cannot be Blank")
    private String address;

    @Min(value = 0, message = "Rating must be greater than or equal to 0")
    private Integer rating;

    @NotNull(message = "Specialization cannot be Null")
    private boolean isActive;

    @NotNull(message = "Specialization cannot be Null")
    private Specialization specialization;

    @NotNull(message = "CreatedAt cannot be Null")
    private Date createdAt;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "User  ID cannot be Null")
    private Integer userId;
}
