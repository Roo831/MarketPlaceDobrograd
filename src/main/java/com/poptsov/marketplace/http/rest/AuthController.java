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

    @PostMapping("/registration")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid RegisterDto request) {
        System.out.println("AuthController register start");
        return authenticationService.signUp(request);

    }

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginDto request) {
        System.out.println("AuthController log in start");
        return authenticationService.signIn(request);

    }
}