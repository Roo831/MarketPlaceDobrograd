package com.poptsov.marketplace.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto implements CreateDto {

    @NotBlank(message = "SteamId cannot be Blank")
    private String steamId;
    @NotBlank(message = "Username cannot be Blank")
    private String username;

   

    @Override
    public String getLoggingCreateInfo() {
        return this.getClass().getSimpleName() + " [username=" + username + ", SteamId" + steamId + "]";
    }
}
