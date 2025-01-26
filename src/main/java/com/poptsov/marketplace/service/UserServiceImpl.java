package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.EntityAlreadyExistsException;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.exceptions.EntityNotFoundException;
import com.poptsov.marketplace.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final ShopReadMapper shopReadMapper;
    private final OrderReadMapper orderReadMapper;

    //CRUD start
    public UserReadDto findById(Integer id) { // Admin
        return userRepository.findUserById(id)
                .map(userReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Failed to get user with id: " + id));
    }

    public List<UserReadDto> findAll() {  // Admin
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(userReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("Пользователь с таким email уже существует");
        }
        return userRepository.save(user);
    }

    @Transactional
    public UserReadDto update(UserEditDto userEditDto) {
        User userToUpdate = findCurrentUser();

        userToUpdate.setFirstname(userEditDto.getFirstname());
        userToUpdate.setLastname(userEditDto.getLastname());

        return userReadMapper.map(userRepository.save(userToUpdate));
    }

    //CRUD end

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find user with email: " + email));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find user with username: " + username));
    }

    public UserDetailsService userDetailsService() {
        return this::findByUsername;
    }

    public User findCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }

    @Transactional
    public UserReadDto updateUserAdminRights(Integer id, SwitchAdminDto switchAdminDto) { // Admin

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User  not found with id: " + id));
        userToUpdate.setIsAdmin(switchAdminDto.isAdmin());
        if (switchAdminDto.isAdmin()) {
            userToUpdate.setRole(Role.admin);
        } else
            userToUpdate.setRole(Role.user);
        return userReadMapper.map(userRepository.save(userToUpdate));
    }


    public UserReadDto getMyself() {
        return userReadMapper.map(findCurrentUser());
    }


    public UserReadDto findOwnerByOrderId(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(Order::getUser)
                .map(userReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Failed to get user for order with id: " + orderId));
    }

    public ShopReadDto findShopByUserId(Integer userId) { // Admin
        return userRepository.findUserById(userId)
                .map(User::getShop)
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Failed to get shop with user id: " + userId));
    }

    public List<OrderReadDto> findOrdersByUserId(Integer userId) {  //Admin
        return userRepository.findUserById(userId)
                .map(User::getOrders)
                .map(orders -> orders.stream()
                        .map(orderReadMapper::map)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new EntityNotFoundException("Failed to get orders for user with id: " + userId));
    }

    public boolean isUserBanned(String username) {
        return userRepository.findByUsername(username)
                .map(User::getIsBanned)
                .orElse(false);
    }
}