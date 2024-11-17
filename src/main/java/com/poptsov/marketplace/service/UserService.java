package com.poptsov.marketplace.service;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.LoginDto;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.dto.UserEditDto;
import com.poptsov.marketplace.exceptions.UserCreateException;
import com.poptsov.marketplace.exceptions.UserUpdateException;
import com.poptsov.marketplace.mapper.UserEditMapper;
import com.poptsov.marketplace.mapper.UserReadMapper;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final UserReadMapper userReadMapper;
    private final UserEditMapper userEditMapper;


    @Transactional
    public UserReadDto create(RegisterDto registerDto) {
        return Optional.of(registerDto) // если дто пришло
                .map(userRegisterMapper::map) // замапить в ентити
                .map(userRepository::save) // сохранить в БД
                .map(userReadMapper::map) // обратно замапить в UserReadDto
                .orElseThrow(() -> new UserCreateException("Failed to create user: " + registerDto.getUsername())); // обработка ошибок
    }

    @Transactional
    public UserReadDto updateUser (Integer id, UserEditDto userEditDto) {

        Optional<UserEditDto> optionalUserEditDto = Optional.of(userEditDto);
        return   userRepository.update(id, userEditMapper.map(userEditDto)) // обновить пользователя
                .map(userReadMapper::map) // Замапить обратно в UserReadDto
                .orElseThrow(() -> new UserUpdateException("Failed to update user with id: " + id)); // Обработка ошибок, если пользователь не найден
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

    public UserReadDto getOwnerByShopId(Integer id) {
    }

    public UserReadDto getUserById(Integer id) {

    }

    public UserReadDto getOwnerByOrderId(Integer id) {
        return null;
    }

    public UserReadDto registerUser(RegisterDto registerDto) {
        return null;
    }

    public UserReadDto authenticateUser(LoginDto loginDto) {
    }



    public List<UserReadDto> getAllUsers() {
        return null;
    }
}
