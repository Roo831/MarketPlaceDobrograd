package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.AuthorizationException;
import com.poptsov.marketplace.mapper.JwtAuthenticationDtoMapper;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import com.poptsov.marketplace.security.JwtService;
import com.poptsov.marketplace.util.VerificationCodeGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Transactional
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterMapper userRegisterMapper;
    private final EmailService emailService;
    private final JwtAuthenticationDtoMapper jwtAuthenticationDtoMapper;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    /**
     * Регистрация пользователя
     *
     * @param registerDto данные пользователя
     * @return токен
     */
    @Transactional
    public OverheadMessageDto create(RegisterDto registerDto) {

        registerDto.setEmail(registerDto.getEmail().toLowerCase(Locale.ROOT));

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

    @Transactional
    public JwtAuthenticationDto verifyCode(VerificationCodeDto verificationCodeDto) {
        String storedCode = verificationCodes.get(verificationCodeDto.getEmail());
        if (storedCode != null && storedCode.equals(verificationCodeDto.getCode())) {

            verificationCodes.remove(verificationCodeDto.getEmail());

            User user = userService.findByEmail(verificationCodeDto.getEmail());
            user.setIsVerified(true);
            var jwt = jwtService.generateToken(user);
            return jwtAuthenticationDtoMapper.map(user, jwt);
        } else {
            throw new IllegalArgumentException("Неверный код подтверждения.");
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param loginDto данные пользователя
     * @return токен
     */
    public JwtAuthenticationDto signIn(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));
        User user = userService.findByUsername(loginDto.getUsername());
        if (!user.getIsVerified()) {
            throw new AuthorizationException("Пользователь не верифицирован.");
        }
        String jwt = jwtService.generateToken(user);
        return jwtAuthenticationDtoMapper.map(user, jwt);
    }
}