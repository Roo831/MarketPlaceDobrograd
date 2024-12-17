package com.poptsov.marketplace.security;

import com.poptsov.marketplace.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Обработка предварительных запросов (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return; // Завершаем обработку, так как это предварительный запрос
        }

        log.info("doFilterInternal start");

        var authHeader = request.getHeader(HEADER_NAME);

        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {

            log.info("Bearer is empty, return filterChain.doFilter");

            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        String username;

        try {

            log.info("Bearer is not empty, get jwt and extract username from jwt...");

            username = jwtService.extractUserName(jwt);

        } catch (ExpiredJwtException e) {

            log.error("JWT expired: {}", e.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT expired");
            response.getWriter().flush();
            return;
        } catch (Exception e) {

            log.error("Error extracting username from JWT: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT");
            response.getWriter().flush();
            return;
        }

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

            log.info("Username not null, and authentication is null, try to get user...");

            UserDetails userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(username);

            log.info("User  found: {}", userDetails.getUsername());

            boolean isBanned = userService.isUserBanned(username);

            log.info("Ban checked for user: {}", username);

            if (isBanned) {
                log.warn("User  {} is banned", username);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access denied for banned users");
                response.getWriter().flush();
                return;
            }
            log.info("User  is not banned.");

            // Если токен валиден, то аутентифицируем пользователя
            log.info("Try to authenticate user...");

            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                log.info("Token is valid, create context...");

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                log.info("Generate new UsernamePasswordAuthenticationToken...");

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);

                log.info("Put authToken into context...");

                SecurityContextHolder.setContext(context);

                log.info("Put context into SecurityContextHolder");
            } else {
                log.warn("Token is invalid for user: {}", username);
            }
        }
        filterChain.doFilter(request, response);
        log.info("doFilterInternal end");
    }
}


