package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final UserRepository userRepository;

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
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
                .build();
    }

    public UserReadDto mapDetails(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User  not found"));

        return UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .steamId(user.getSteamId())
                .role(user.getRole())
                .isAdmin(user.getIsAdmin())
                .isBanned(user.getIsBanned())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
