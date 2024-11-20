package com.poptsov.marketplace.service;


import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.UserCreateException;
import com.poptsov.marketplace.exceptions.UserGetException;
import com.poptsov.marketplace.exceptions.UserUpdateException;
import com.poptsov.marketplace.mapper.UserEditMapper;
import com.poptsov.marketplace.mapper.UserReadMapper;
import com.poptsov.marketplace.mapper.UserRegisterMapper;

import com.poptsov.marketplace.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Transactional(readOnly = true)
    @Service
    @RequiredArgsConstructor
    public class UserService {

        private final UserRepository userRepository;
        private final UserReadMapper userReadMapper;
        private final UserEditMapper userEditMapper;
        private final UserRoleMapper userRoleMapper;

        @Transactional
        public User save(User user) {
            System.out.println("Сохранить в БД");
            return userRepository.save(user);
        }


        @Transactional
        public User create(User user) {
            System.out.println("Проверить пользователя на наличие в БД его имени");
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new RuntimeException("Пользователь с таким именем уже существует");
            }

            System.out.println("Проверить пользователя на наличие в БД его почты");
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Пользователь с таким email уже существует");
            }
            return save(user);
        }

        public User getByUsername(String username) {
            System.out.println("Попытаться получить user по его username");
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        }

        public UserDetailsService userDetailsService() {
            return this::getByUsername;
        }

        public User getCurrentUser() {
            System.out.println("Получение имени пользователя из контекста Spring Security");
            var username = SecurityContextHolder.getContext().getAuthentication().getName();
            return getByUsername(username);
        }



    @Transactional
    public UserReadDto updateUser(Integer id, UserEditDto userEditDto) {
        return Optional.of(userRepository.save(userEditMapper.map(id, userEditDto))) // обновить пользователя
                .map(userReadMapper::map) // Замапить обратно в UserReadDto
                .orElseThrow(() -> new UserUpdateException("Failed to update user with id: " + id)); // Обработка ошибок, если пользователь не найден
    }

    @Transactional
    public UserReadDto updateUser(Integer id, UserRoleDto userRoleDto) {

        return Optional.of(userRepository.save(userRoleMapper.map(id, userRoleDto))) // обновить пользователя
                .map(userReadMapper::map) // Замапить обратно в UserReadDto
                .orElseThrow(() -> new UserUpdateException("Failed to update user with id: " + id)); // Обработка ошибок, если пользователь не найден
    }

    public UserReadDto getUserById(Integer id) {
        return userRepository.findUserById(id)
                .map(userReadMapper::map)
                .orElseThrow(() -> new UserGetException("Failed to get user with id: " + id));
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


    public UserReadDto getOwnerByOrderId(Integer orderId) {
        return null;
    }

    public UserReadDto getOwnerByShopId(Integer shopId) {
        return null;
    }

    public ShopReadDto getShopByUserId(Integer id) {
        return null;
    }

    public List<OrderReadDto> getOrdersByUserId(Integer id) {
        return null;
    }

    public OrderReadDto getOrderByUserId(Integer id) {
        return null;
    }
}





