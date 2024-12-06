package com.poptsov.marketplace.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;

    private final String username = "Test Username";

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        jwtService.setJwtSigningKey("q6b+8X0O8zIYFhG4u4n5nJ3hK9x9m9QWm6H6xW9f0uI=");
        userDetails = getUser();
    }

    private UserDetails getUser () {
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password("Test Password")
                .roles("user")
                .build();
    }


    @Test
    void extractUserNameTest() {
        String token = jwtService.generateToken(userDetails);
        assertEquals(username, jwtService.extractUserName(token));
    }

    @Test
    void generateTokenTest() {
        String token = jwtService.generateToken(userDetails);
        assertEquals(username, jwtService.extractUserName(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValidTest() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenInvalidTest() {
        String invalidToken = "invalid.token";
        assertThrows(MalformedJwtException.class, () -> jwtService.isTokenValid(invalidToken, userDetails));
    }

    @Test
    void isTokenNonExpiredTest() {
        String token = jwtService.generateToken(userDetails);
        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    void isTokenExpiredTest() {
        String token = jwtService.generateTokenWithExpiredDate(userDetails);

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenExpired(token));
    }


}
