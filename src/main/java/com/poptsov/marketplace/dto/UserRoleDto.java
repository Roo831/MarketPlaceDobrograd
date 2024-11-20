package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDto {

    @NotNull(message = "Role cannot be Null")
    private Role role;

}
