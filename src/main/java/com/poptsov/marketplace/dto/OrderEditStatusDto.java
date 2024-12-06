package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderEditStatusDto implements UpdateDto {

    @NotNull(message = "Status cannot be Null")
    private Status status;

    @Override
    public String getLoggingUpdateInfo() {
        return this.getClass().getSimpleName() + " [status=" + status + "]";
    }
}