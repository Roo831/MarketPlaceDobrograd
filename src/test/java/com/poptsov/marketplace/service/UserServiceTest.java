package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Role;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.EntityAlreadyException;

import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.exceptions.EntityNotFoundException;
import com.poptsov.marketplace.mapper.OrderReadMapper;
import com.poptsov.marketplace.mapper.ShopReadMapper;
import com.poptsov.marketplace.mapper.UserReadMapper;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;

    @Mock
    private ShopReadMapper shopReadMapper;

    @Mock
    private OrderReadMapper orderReadMapper;

    private User user;

    private Order order;

    private Shop shop;

    private final Integer userId = 1;

    private final Integer orderId = 1;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = MockEntityUtil.getTestUser();
        order = MockEntityUtil.getTestOrder();
        shop = MockEntityUtil.getTestShop();

        user.setShop(shop);
        user.setOrders(List.of(order)); // Делает заказ в своем же магазине - бизнес-логика такое позволяет, поэтому это реализовано для упрощения тестирования.

        shop.setUser(user);
        shop.setOrders(List.of(order));

        order.setUser(user);
        order.setShop(shop);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void findById_get() {

        // Arrange
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        when(userReadMapper.map(user)).thenReturn(UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .steamId(user.getSteamId())
                .role(user.getRole())
                .isAdmin(user.getIsAdmin())
                .isBanned(user.getIsBanned())
                .createdAt(user.getCreatedAt())
                .build());

        //Actual
        UserReadDto userReadDto = userService.findById(userId);

        //Assert
        assertNotNull(userReadDto);
        assertEquals(user.getId(), userReadDto.getId());
        assertEquals(user.getUsername(), userReadDto.getUsername());
        assertEquals(user.getEmail(), userReadDto.getEmail());
        assertEquals(user.getFirstname(), userReadDto.getFirstname());
        assertEquals(user.getLastname(), userReadDto.getLastname());
        assertEquals(user.getSteamId(), userReadDto.getSteamId());
        assertEquals(user.getRole(), userReadDto.getRole());
        assertEquals(user.getIsAdmin(), userReadDto.getIsAdmin());
        assertEquals(user.getIsBanned(), userReadDto.getIsBanned());
        assertEquals(user.getCreatedAt(), userReadDto.getCreatedAt());

    }

    @Test
    void findById_throw() {
        // Arrange
        when(userRepository.findUserById(userId)).thenThrow(EntityGetException.class);

        //Assert, Actual
        assertThrows(EntityGetException.class, () -> userService.findById(userId));

    }

    @Test
    void findAll_get() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userReadMapper.map(user)).thenReturn(UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .steamId(user.getSteamId())
                .role(user.getRole())
                .isAdmin(user.getIsAdmin())
                .isBanned(user.getIsBanned())
                .createdAt(user.getCreatedAt())
                .build());

        List<UserReadDto> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
        assertEquals(user.getUsername(), users.get(0).getUsername());
        assertEquals(user.getEmail(), users.get(0).getEmail());
        assertEquals(user.getFirstname(), users.get(0).getFirstname());
        assertEquals(user.getLastname(), users.get(0).getLastname());
        assertEquals(user.getSteamId(), users.get(0).getSteamId());
        assertEquals(user.getRole(), users.get(0).getRole());
        assertEquals(user.getIsAdmin(), users.get(0).getIsAdmin());
        assertEquals(user.getIsBanned(), users.get(0).getIsBanned());
        assertEquals(user.getCreatedAt(), users.get(0).getCreatedAt());

    }

    @Test
    void findAll_getBlank() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserReadDto> users = userService.findAll();

        assertNotNull(users);
        assertEquals(0, users.size());


    }

    @Test
    void create_get() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.create(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getSteamId(), result.getSteamId());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getIsAdmin(), result.getIsAdmin());
        assertEquals(user.getIsBanned(), result.getIsBanned());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());

    }

    @Test
    void create_throw() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        assertThrows(EntityAlreadyException.class, () -> userService.create(user));
    }

    @Test
    void update_get() {
        String updatedFirstname = "Edit Test User Firstname";
        String updatedLastname = "Edit Test User Lastname";

        UserEditDto userEditDto = UserEditDto.builder()
                .firstname(updatedFirstname)
                .lastname(updatedLastname)
                .build();

        User expectedResult = user;
        expectedResult.setFirstname(updatedFirstname);
        expectedResult.setLastname(updatedLastname);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userReadMapper.map(expectedResult)).thenReturn(UserReadDto.builder()
                .id(expectedResult.getId())
                .username(expectedResult.getUsername())
                .email(expectedResult.getEmail())
                .firstname(expectedResult.getFirstname())
                .lastname(expectedResult.getLastname())
                .steamId(expectedResult.getSteamId())
                .role(expectedResult.getRole())
                .isAdmin(expectedResult.getIsAdmin())
                .isBanned(expectedResult.getIsBanned())
                .createdAt(expectedResult.getCreatedAt())
                .build());
        when(userRepository.save(any())).thenReturn(expectedResult);

        UserReadDto actualResult = userService.update(userEditDto);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getUsername(), actualResult.getUsername());
        assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        assertEquals(expectedResult.getFirstname(), actualResult.getFirstname());
        assertEquals(expectedResult.getLastname(), actualResult.getLastname());
        assertEquals(expectedResult.getSteamId(), actualResult.getSteamId());
        assertEquals(expectedResult.getRole(), actualResult.getRole());
        assertEquals(expectedResult.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(expectedResult.getIsBanned(), actualResult.getIsBanned());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());

    }

    @Test
    void findByEmail_get() {
        String email = user.getEmail();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User result = userService.findByEmail(email);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getSteamId(), result.getSteamId());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getIsAdmin(), result.getIsAdmin());
        assertEquals(user.getIsBanned(), result.getIsBanned());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());

    }

    @Test
    void findByEmail_throw() {

        String email = user.getEmail();

        when(userRepository.findByEmail(email)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(email));
    }

    @Test
    void findByUsername_get() {
        String username = user.getUsername();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        User result = userService.findByUsername(username);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstname(), result.getFirstname());
        assertEquals(user.getLastname(), result.getLastname());
        assertEquals(user.getSteamId(), result.getSteamId());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getIsAdmin(), result.getIsAdmin());
        assertEquals(user.getIsBanned(), result.getIsBanned());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void findByUsername_throw() {

        String username = user.getUsername();

        when(userRepository.findByUsername(username)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(username));
    }

    @Test
    void updateUserToAdmin_setAdminInTrue() {

        SwitchAdminDto switchAdminDto = SwitchAdminDto.builder()
                .admin(true)
                .build();
        User expectedResult = user;
        expectedResult.setRole(Role.user);
        expectedResult.setIsAdmin(switchAdminDto.isAdmin());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(expectedResult);
        when(userReadMapper.map(expectedResult)).thenReturn(UserReadDto.builder()
                .id(expectedResult.getId())
                .username(expectedResult.getUsername())
                .email(expectedResult.getEmail())
                .firstname(expectedResult.getFirstname())
                .lastname(expectedResult.getLastname())
                .steamId(expectedResult.getSteamId())
                .isAdmin(expectedResult.getIsAdmin())
                .isBanned(expectedResult.getIsBanned())
                .createdAt(expectedResult.getCreatedAt())
                .build());

        UserReadDto actualResult = userService.updateUserAdminRights(userId, switchAdminDto);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getUsername(), actualResult.getUsername());
        assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        assertEquals(expectedResult.getFirstname(), actualResult.getFirstname());
        assertEquals(expectedResult.getLastname(), actualResult.getLastname());
        assertEquals(expectedResult.getSteamId(), actualResult.getSteamId());
        assertEquals(expectedResult.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(expectedResult.getIsBanned(), actualResult.getIsBanned());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
    }

    @Test
    void updateUserToAdmin_setAdminInFalse() {

        SwitchAdminDto switchAdminDto = SwitchAdminDto.builder()
                .admin(false)
                .build();
        User expectedResult = user;
        expectedResult.setRole(Role.admin);
        expectedResult.setIsAdmin(switchAdminDto.isAdmin());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(expectedResult);
        when(userReadMapper.map(expectedResult)).thenReturn(UserReadDto.builder()
                .id(expectedResult.getId())
                .username(expectedResult.getUsername())
                .email(expectedResult.getEmail())
                .firstname(expectedResult.getFirstname())
                .lastname(expectedResult.getLastname())
                .steamId(expectedResult.getSteamId())
                .isAdmin(expectedResult.getIsAdmin())
                .isBanned(expectedResult.getIsBanned())
                .createdAt(expectedResult.getCreatedAt())
                .build());

        UserReadDto actualResult = userService.updateUserAdminRights(userId, switchAdminDto);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getUsername(), actualResult.getUsername());
        assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        assertEquals(expectedResult.getFirstname(), actualResult.getFirstname());
        assertEquals(expectedResult.getLastname(), actualResult.getLastname());
        assertEquals(expectedResult.getSteamId(), actualResult.getSteamId());
        assertEquals(expectedResult.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(expectedResult.getIsBanned(), actualResult.getIsBanned());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());

    }

    @Test
    void updateUserToAdmin_throw() {
        SwitchAdminDto switchAdminDto = SwitchAdminDto.builder()
                .admin(true)
                .build();
        when(userRepository.findById(userId)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> userService.updateUserAdminRights(userId, switchAdminDto));
    }

    @Test
    void findOwnerByOrderId_get() {

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        UserReadDto expectedResult = UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .steamId(user.getSteamId())
                .isAdmin(user.getIsAdmin())
                .isBanned(user.getIsBanned())
                .createdAt(user.getCreatedAt())
                .build();
        when(userReadMapper.map(user)).thenReturn(expectedResult);

        UserReadDto actualResult = userService.findOwnerByOrderId(orderId);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getUsername(), actualResult.getUsername());
        assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        assertEquals(expectedResult.getFirstname(), actualResult.getFirstname());
        assertEquals(expectedResult.getLastname(), actualResult.getLastname());
        assertEquals(expectedResult.getSteamId(), actualResult.getSteamId());
        assertEquals(expectedResult.getIsAdmin(), actualResult.getIsAdmin());
        assertEquals(expectedResult.getIsBanned(), actualResult.getIsBanned());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
    }

    @Test
    void findOwnerByOrderId_throw() {

        when(orderRepository.findById(orderId)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> userService.findOwnerByOrderId(orderId));
    }

    @Test
    void findShopByUserId_get() {

        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));

        ShopReadDto expectedResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(userId)
                .build();

        when(shopReadMapper.map(shop)).thenReturn(expectedResult);

        ShopReadDto actualResult = userService.findShopByUserId(userId);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getName(), actualResult.getName());
        assertEquals(expectedResult.getAddress(), actualResult.getAddress());
        assertEquals(expectedResult.getRating(), actualResult.getRating());
        assertEquals(expectedResult.isActive(), actualResult.isActive());
        assertEquals(expectedResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        assertEquals(expectedResult.getUserId(), actualResult.getUserId());

    }

    @Test
    void findShopByUserId_throw() {
        when(userRepository.findUserById(userId)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> userService.findShopByUserId(userId));

    }

    @Test
    void findOrdersByUserId_get() {

        OrderReadDto expectedResult = OrderReadDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .userId(userId)
                .shopId(shop.getId())
                .build();
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));


        when(orderReadMapper.map(any())).thenReturn(expectedResult);

        List<OrderReadDto> actualResultList = userService.findOrdersByUserId(userId);
        OrderReadDto actualResult = actualResultList.get(0);
        assertNotNull(actualResultList);
        assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        assertEquals(expectedResult.getName(), actualResult.getName());
        assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        assertEquals(expectedResult.getPrice(), actualResult.getPrice());
        assertEquals(expectedResult.getUserId(), actualResult.getUserId());
    }

    @Test
    void findOrdersByUserId_throw() {
        when(userRepository.findUserById(userId)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> userService.findOrdersByUserId(userId));
    }

    @Test
    void isUserBanned_getTrue() {
        String username = user.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        user.setIsBanned(true);
        boolean expectedResult = true;

        boolean actualResult = userService.isUserBanned(username);
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void isUserBanned_getFalse() {
        String username = user.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        user.setIsBanned(false);
        boolean expectedResult = false;

        boolean actualResult = userService.isUserBanned(username);
        assertEquals(expectedResult, actualResult);
    }

}