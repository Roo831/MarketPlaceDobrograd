package com.poptsov.marketplace.security;

import com.poptsov.marketplace.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetailsService userDetailsService;

    private final String validToken = "valid.jwt.token";
    private final String username = "testUser";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(userService.userDetailsService()).thenReturn(userDetailsService);
    }

    @Test
    void doFilterInternal_ValidToken_SetsAuthentication() throws ServletException, IOException {
        // Arrange
        when(request.getHeader(JwtAuthenticationFilter.HEADER_NAME)).thenReturn(JwtAuthenticationFilter.BEARER_PREFIX + validToken);
        when(jwtService.extractUserName(validToken)).thenReturn(username);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(validToken, userDetails)).thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }


    @Test
    void doFilterInternal_InvalidToken_ReturnsUnauthorized() throws ServletException, IOException {
        // Arrange
        when(request.getHeader(JwtAuthenticationFilter.HEADER_NAME)).thenReturn(JwtAuthenticationFilter.BEARER_PREFIX + validToken);
        when(jwtService.extractUserName(validToken)).thenThrow(new RuntimeException("Invalid token"));
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Invalid JWT");
        verify(filterChain, never()).doFilter(request, response);
    }


}