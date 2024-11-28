package com.poptsov.marketplace.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto implements CreateDto {

    @NotBlank(message = "Username cannot be Blank")
    private String username;

    @NotBlank(message = "Email cannot be Blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be Blank")
    private String password;

    @NotBlank(message = "Firstname cannot be Blank")
    private String firstname;

    @NotBlank(message = "Lastname cannot be Blank")
    private String lastname;

    @Override
    public String getLoggingCreateInfo() {
        return this.getClass().getSimpleName() + " [username=" + username + ", email" + email + "]";
    }
}
