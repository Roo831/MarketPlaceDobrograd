package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.JwtAuthenticationDto;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationDtoMapper implements Mapper<User, JwtAuthenticationDto> {


    @Override
    public JwtAuthenticationDto map(User object) {

        return JwtAuthenticationDto.builder()
                .id(object.getId())
                .email(object.getEmail())
                .username(object.getUsername())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .steamId(object.getSteamId())
                .role(object.getRole())
                .isAdmin(object.getIsAdmin())
                .isBanned(object.getIsBanned())
                .createdAt(object.getCreatedAt())
                .build();
    }

    public JwtAuthenticationDto map(User object, String jwt) {

        return JwtAuthenticationDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .email(object.getEmail())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .steamId(object.getSteamId())
                .role(object.getRole())
                .isAdmin(object.getIsAdmin())
                .isBanned(object.getIsBanned())
                .createdAt(object.getCreatedAt())
                .token(jwt)
                .build();
    }
}
