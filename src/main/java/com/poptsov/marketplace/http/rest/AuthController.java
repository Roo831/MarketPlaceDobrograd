package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.dto.LoginDto;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.dto.UserDto;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> registerUser (@RequestBody RegisterDto registerDto) {
        // Логика регистрации пользователя
        UserDto userDto = userService.registerUser (registerDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser (@RequestBody LoginDto loginDto) {
        // Логика аутентификации пользователя
        UserDto userDto = userService.authenticateUser (loginDto);
        return ResponseEntity.ok(userDto);
    }
}