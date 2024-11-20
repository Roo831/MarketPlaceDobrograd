package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.JwtAuthenticationResponse;
import com.poptsov.marketplace.dto.LoginDto;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import com.poptsov.marketplace.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
    public JwtAuthenticationResponse signUp(RegisterDto registerDto) {
        System.out.println("Build user from dto");
        User user = userRegisterMapper.map(registerDto);
        System.out.println(("try to create user in DB"));
        userService.create(user);
        System.out.println("try to generate token");
        var jwt = jwtService.generateToken(user);
        System.out.println("return JwtAuthenticationResponse: " + jwt);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(LoginDto request) {
        System.out.println("authenticationManager.authenticate(data from DTO)");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwt);
    }

}