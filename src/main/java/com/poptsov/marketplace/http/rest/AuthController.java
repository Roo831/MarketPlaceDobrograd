package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.dto.LoginDto;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserReadDto> registerUser (@Validated @RequestBody RegisterDto registerDto) {
        // Логика регистрации пользователя
        UserReadDto userReadDto = userService.registerUser(registerDto);
        return ResponseEntity.ok(userReadDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserReadDto> loginUser (@Validated @RequestBody LoginDto loginDto) {
        // Логика аутентификации пользователя
        UserReadDto userReadDto = userService.authenticateUser(loginDto);
        return ResponseEntity.ok(userReadDto);
    }
}