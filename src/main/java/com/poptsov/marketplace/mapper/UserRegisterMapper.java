package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RequiredArgsConstructor
public class UserRegisterMapper implements Mapper<RegisterDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(RegisterDto registerDto) {
        return User.builder()
                .username(registerDto.getUsername())
                .firstname(registerDto.getFirstname())
                .lastname(registerDto.getLastname())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.user)
                .isAdmin(false)
                .isBanned(false)
                .isVerified(false)
                .createdAt(new Date())
                .build();
    }
}
