package com.poptsov.marketplace.service;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.LoginDto;
import com.poptsov.marketplace.dto.RegisterDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.dto.UserEditDto;
import com.poptsov.marketplace.exceptions.UserCreateException;
import com.poptsov.marketplace.exceptions.UserGetException;
import com.poptsov.marketplace.exceptions.UserUpdateException;
import com.poptsov.marketplace.mapper.UserEditMapper;
import com.poptsov.marketplace.mapper.UserReadMapper;
import com.poptsov.marketplace.mapper.UserRegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final UserReadMapper userReadMapper;
    private final UserEditMapper userEditMapper;


    @Transactional
    public UserReadDto updateUser(Integer id, UserEditDto userEditDto) {
        return Optional.of(userRepository.save(userEditMapper.map(id, userEditDto))) // обновить пользователя
                .map(userReadMapper::map) // Замапить обратно в UserReadDto
                .orElseThrow(() -> new UserUpdateException("Failed to update user with id: " + id)); // Обработка ошибок, если пользователь не найден
    }

    public UserReadDto getUserById(Integer id) {
        return userRepository.getUserById(id)
                .map(userReadMapper::map)
                .orElseThrow(() -> new UserGetException("Failed to get user with id: " + id));
    }

    @Transactional
    public UserReadDto registerUser(RegisterDto registerDto) {
        return Optional.of(registerDto) // если дто пришло
                .map(userRegisterMapper::map) // замапить в ентити
                .map(userRepository::save) // сохранить в БД
                .map(userReadMapper::map) // обратно замапить в UserReadDto
                .orElseThrow(() -> new UserCreateException("Failed to create user: " + registerDto.getUsername())); // обработка ошибок
    }

    public List<UserReadDto> getAllUsers() {
        List<User> users = userRepository.findAll(); // Получаем список пользователей

        if (users.isEmpty()) {
            throw new UserGetException("No users found"); // Выбрасываем исключение, если список пуст
        }

        return users.stream()
                .map(userReadMapper::map) // Маппим каждую сущность User в UserReadDto
                .collect(Collectors.toList()); // Собираем в список
    }


    public UserReadDto authenticateUser(LoginDto loginDto) {

        return null;
    }

    public UserReadDto getOwnerByOrderId(Integer orderId) {
        return null;
    }

    public UserReadDto getOwnerByShopId(Integer shopId) {
        return null;
    }


}
