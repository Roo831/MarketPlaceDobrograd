package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.JwtAuthenticationResponse;
import com.poptsov.marketplace.dto.LoginDto;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import com.poptsov.marketplace.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterMapper userRegisterMapper;

    /**
     * Регистрация пользователя
     *
     * @param registerDto данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse create(RegisterDto registerDto) {
        User user = userRegisterMapper.map(registerDto);
        userService.create(user);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
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
        log.info("Sign In successful!, return token - {}", jwt);
        return new JwtAuthenticationResponse(jwt);
    }
}