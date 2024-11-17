package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .username(object.getUsername())
                .email(object.getEmail())
                .password(object.getPassword())
                .firstname(object.getFirstname())
                .lastname(object.getLastname())
                .steamId(object.getSteamId())
                .role(object.getRole())
                .isAdmin(object.getIsAdmin())
                .isBanned(object.getIsBanned())
                .createdAt(object.getCreatedAt())
                .build();
    }
}
