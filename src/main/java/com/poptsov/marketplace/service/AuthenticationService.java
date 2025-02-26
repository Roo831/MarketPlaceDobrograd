package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.*;

public interface AuthenticationService extends CreateService<OverheadMessageDto, RegisterDto> {

    @Override
    OverheadMessageDto create(RegisterDto registerDto);

    /**
     * Валидация почтового адреса
     *
     * @param verificationCodeDto почта и код подтверждения
     * @return JwtAuthenticationResponse
     * @throws IllegalArgumentException Неверный код
     */


    JwtAuthenticationDto verifyCode(VerificationCodeDto verificationCodeDto);

    /**
     * Аутентификация пользователя
     *
     * @param loginDto данные пользователя
     * @return токен
     */
    JwtAuthenticationDto signIn(LoginDto loginDto);
}
