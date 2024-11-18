//package com.poptsov.marketplace.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import static com.poptsov.marketplace.database.entity.Role.admin;
//import static com.poptsov.marketplace.database.entity.Role.user;
//import static com.poptsov.marketplace.database.entity.Role.banned;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
//
//
//    @Configuration
//    @EnableWebSecurity
//    @EnableMethodSecurity(prePostEnabled = true)
//    public class SecurityConfiguration {
//
////        @Bean
////        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Настроить права доступа
////            http
////                    .csrf(CsrfConfigurer::disable)
////                    .authorizeHttpRequests(auth -> auth
////                            .requestMatchers("/login", "/users/registration","/v3/api-docs/", "/swagger-ui/").permitAll()
////                            .requestMatchers("/admin/**").hasRole(admin.getAuthority())
////                            .requestMatchers(antMatcher("/users/{\\d}/delete")).hasAnyAuthority(admin.getAuthority())
////                            .anyRequest().authenticated())
//////                .httpBasic(Customizer.withDefaults())
////                    .formLogin(login -> login.loginPage("/login")
////                            .defaultSuccessUrl("/shops")
////                            .permitAll())
////                    .logout(logout -> logout
////                            .logoutUrl("/logout")
////                            .logoutSuccessUrl("/login")
////                            .deleteCookies("JSESSIONID"));
////
////            return http.build();
////        }
//    }

