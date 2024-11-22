package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.EntityAlreadyException;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final ShopReadMapper shopReadMapper;
    private final OrderReadMapper orderReadMapper;

    @Transactional
    public User save(User user) {
        System.out.println("Сохранить в БД");
        return userRepository.save(user);
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityAlreadyException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Transactional
    public UserReadDto updateUser (UserEditDto userEditDto) {
        User userToUpdate = getCurrentUser();

        userToUpdate.setFirstname(userEditDto.getFirstname());
        userToUpdate.setLastname(userEditDto.getLastname());

        return userReadMapper.map(userRepository.save(userToUpdate));
    }

    @Transactional
    public UserReadDto updateUser(Integer id, SwitchAdminDto switchAdminDto) { // Admin

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityGetException("User  not found with id: " + id));
        userToUpdate.setIsAdmin(switchAdminDto.isAdmin());
        if(switchAdminDto.isAdmin()) {
            userToUpdate.setRole(Role.admin);
        } else
            userToUpdate.setRole(Role.user);
        return userReadMapper.map(userRepository.save(userToUpdate));
    }


    public UserReadDto getMyself(){
       return userReadMapper.map(getCurrentUser());
    }

    public UserReadDto getUserById(Integer id) { // Admin
        return userRepository.findUserById(id)
                .map(userReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Failed to get user with id: " + id));
    }

    public List<UserReadDto> getAllUsers() {  // Admin
        return Optional.of(userRepository.findAll())
                .filter(users -> !users.isEmpty())
                .map(users -> users.stream()
                        .map(userReadMapper::map)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new EntityGetException("No users found"));
    }

    public UserReadDto getOwnerByOrderId(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(Order::getUser)
                .map(userReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Failed to get user for order with id: " + orderId));
    }

    public ShopReadDto getShopByUserId(Integer id) { // Admin
        return userRepository.findUserById(id)
                .map(User::getShop)
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Failed to get shop with user id: " + id));
    }

    public List<OrderReadDto> getOrdersByUserId(Integer id) {  //Admin
        return userRepository.findUserById(id)
                .map(User::getOrders)
                .map(orders -> orders.stream()
                        .map(orderReadMapper::map)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new EntityGetException("Failed to get orders for user with id: " + id)); // Выбрасываем исключение, если пользователь не найден
    }

    public boolean isUserBanned(String username) {
        return userRepository.findByUsername(username)
                .map(User::getIsBanned)
                .orElse(false);
    }
}





