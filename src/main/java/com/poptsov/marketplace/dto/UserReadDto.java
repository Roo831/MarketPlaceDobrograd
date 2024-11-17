package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Role;

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
public class UserReadDto {

    @NotNull(message = "ID cannot be Null")
    private Integer id;

    @NotBlank(message = "Username cannot be Blank")
    private String username;

    @NotBlank(message = "Email cannot be Blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be Blank")
    private String password;

    @NotBlank(message = "Firstname cannot be Blank")
    private String firstname;

    @NotBlank(message = "Lastname is cannot be Blank")
    private String lastname;

    private String steamId;

    @NotNull(message = "Role cannot be Null")
    private Role role;

    private Boolean isAdmin;

    private Boolean isBanned;

    @NotNull(message = "CreatedAt cannot be Null")
    private Date createdAt;
}
