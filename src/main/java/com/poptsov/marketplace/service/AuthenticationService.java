package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import com.poptsov.marketplace.security.JwtService;
import com.poptsov.marketplace.util.VerificationCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterMapper userRegisterMapper;
    private final EmailService emailService;

    private final Map<String, String> verificationCodes = new HashMap<>();

    /**
     * Регистрация пользователя
     *
     * @param registerDto данные пользователя
     * @return токен
     */
    public OverheadMessageDto create(RegisterDto registerDto) {
        User user = userRegisterMapper.map(registerDto);
        userService.create(user);

        String code = VerificationCodeGenerator.generateCode();
        verificationCodes.put(user.getEmail(), code);
        emailService.sendVerificationCode(user.getEmail(), code);


        return new OverheadMessageDto("Код был отправлен на вашу почту");
    }

    /**
     * Валидация почтового адреса
     * @param verificationCodeDto почта и код подтверждения
     * @return JwtAuthenticationResponse
     * @throws IllegalArgumentException Неверный код
     */

    public JwtAuthenticationResponse verifyCode(VerificationCodeDto verificationCodeDto) {
        String storedCode = verificationCodes.get(verificationCodeDto.getEmail());
        if (storedCode != null && storedCode.equals(verificationCodeDto.getCode())) {
            verificationCodes.remove(verificationCodeDto.getEmail());

            User user = userService.findByEmail(verificationCodeDto.getEmail());
            var jwt = jwtService.generateToken(user);
            return new JwtAuthenticationResponse(jwt);
        } else {
            throw new IllegalArgumentException("Неверный код подтверждения.");
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(LoginDto request) {
        log.info("Sign In process start...");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        log.info("Try to get user by Username...");
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());
        log.info("Try to generate token...");
        var jwt = jwtService.generateToken(user);
        log.info("Sign In successful!, return token.");
        return new JwtAuthenticationResponse(jwt);
    }
}