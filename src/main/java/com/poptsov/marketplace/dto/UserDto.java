package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Role;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "ID is required")
    private Integer id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Firstname is required")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    private String steamId;

    @NotNull(message = "Role is required")
    private Role role;

    private Boolean isAdmin;

    private Boolean isBanned;

    @NotNull(message = "CreatedAt is required")
    private Date createdAt;
}
