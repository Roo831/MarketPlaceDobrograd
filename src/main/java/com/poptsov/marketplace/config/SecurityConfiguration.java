package com.poptsov.marketplace.config;


import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.security.JwtAuthenticationFilter;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class SecurityConfiguration {

    private final UserService userService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());
                })
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests

                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/shops/{id}").permitAll()
                        .requestMatchers("/users/{id}").permitAll()
                        .requestMatchers("/shops/active").permitAll()
                        .requestMatchers("/swagger-ui/index.html", "/v3/api-docs", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/shops/**", "/users/**", "/orders/**").hasAnyAuthority(Role.user.name(), Role.admin.name())
                        .requestMatchers("/admin/**").hasAnyAuthority(Role.admin.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(
                List.of("http://localhost:80", "https://localhost:443", // nginx
                        "http://rubronameg.temp.swtest.ru", // frontend
                        "http://176.59.5.10:8080", // QA
                        "http://176.59.5.10:80", // QA
                        "https://steamcommunity.com", // Steam
                        "http://steamcommunity.com", // Steam
                        "http://localhost:8081"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        var urlBasedConfig = new UrlBasedCorsConfigurationSource();
        urlBasedConfig.registerCorsConfiguration("/**", corsConfig);
        return urlBasedConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}



