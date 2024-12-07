package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


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
    public ResponseEntity<OverheadMessageDto> signUp(@Validated @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authenticationService.create(registerDto));

    }

    /**
     * Валидация почтового адреса
     * @param verificationCodeDto почта и код подтверждения
     * @return JwtAuthenticationResponse
     */

    @PostMapping("/verify-code")
    public ResponseEntity<JwtAuthenticationResponse> verifyCode(@Validated @RequestBody VerificationCodeDto verificationCodeDto) {
        return ResponseEntity.ok(authenticationService.verifyCode(verificationCodeDto));
    }

    /**
     * Аутентификация пользователя
     *
     * @param loginDto Данные пользователя
     * @return токен JwtAuthenticationResponse
     */

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@Validated @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authenticationService.signIn(loginDto));

    }

    @GetMapping("/test")
  public ResponseEntity<OverheadMessageDto> test() {
      return ResponseEntity.ok(OverheadMessageDto.builder()
                      .message("test")
              .build());
    }
}