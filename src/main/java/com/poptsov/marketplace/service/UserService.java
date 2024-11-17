package com.poptsov.marketplace.service;


import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Transactional
    public UserDto create(RegisterDto registerDto) {
        return Optional.of(registerDto) // если дто пришло
                .map(dto -> {
                    return userCreateEditMapper.map(dto);
                }) // замапить в ентити
                .map(userRepository::save) // сохранить в БД
                .map(userReadMapper::map) // обратно замапить в UserReadDto
                .orElseThrow(); // обработка ошибок
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrive user: " + username));

    }

}
