package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


@Validated
@RestController
@CrossOrigin
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
    public OverheadMessageDto signUp(@Validated @RequestBody RegisterDto registerDto) {
        return authenticationService.create(registerDto);

    }

    /**
     * Валидация почтового адреса
     * @param verificationCodeDto почта и код подтверждения
     * @return JwtAuthenticationResponse
     */

    @PostMapping("/verify-code")
    public JwtAuthenticationResponse verifyCode(@Validated @RequestBody VerificationCodeDto verificationCodeDto) {
        return authenticationService.verifyCode(verificationCodeDto);
    }

    /**
     * Аутентификация пользователя
     *
     * @param loginDto Данные пользователя
     * @return токен JwtAuthenticationResponse
     */

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@Validated @RequestBody LoginDto loginDto) {
        return authenticationService.signIn(loginDto);

    }
}