package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authenticationService;

    /**
     * Регистрация пользователя
     *
     * @param registerDto Данные пользователя
     * @return токен JwtAuthenticationResponse
     */

    @PostMapping("/registration")
    public JwtAuthenticationResponse signUp(@Validated @RequestBody @Valid RegisterDto registerDto) {
        System.out.println("AuthController register start");
        return authenticationService.signUp(registerDto);

    }

    /**
     * Аутентификация пользователя
     *
     * @param loginDto Данные пользователя
     * @return токен JwtAuthenticationResponse
     */

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@Validated @RequestBody @Valid LoginDto loginDto) {
        System.out.println("AuthController login start");
        return authenticationService.signIn(loginDto);

    }
}