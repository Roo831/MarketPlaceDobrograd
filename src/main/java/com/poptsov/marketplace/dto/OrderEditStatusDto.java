package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEditStatusDto {

    @NotNull(message = "Status cannot be Null")
    private Status status;
}