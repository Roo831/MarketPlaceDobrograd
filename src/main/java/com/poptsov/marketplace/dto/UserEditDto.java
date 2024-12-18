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
public class UserEditDto implements UpdateDto {

    @NotBlank(message = "Firstname cannot be Blank")
    private String firstname;

    @NotBlank(message = "Lastname cannot be Blank")
    private String lastname;

    @Override
    public String getLoggingUpdateInfo() {
        return this.getClass().getSimpleName() + " [firstname=" + firstname + ", lastname=" + lastname + "]";
    }
}