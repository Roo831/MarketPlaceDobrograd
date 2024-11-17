package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.OrderService;
import com.poptsov.marketplace.service.ShopService;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final ShopService shopService;


    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> usersDto = userService.getAllUsers();
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> editUser (@PathVariable Integer id, @Validated @RequestBody UserEditDto userEditDto) {
        UserDto updatedUserDto = userService.updateUser (id, userEditDto);
        return ResponseEntity.ok(updatedUserDto);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Integer id) {
        List<OrderDto> orders = orderService.getOrdersByUserId(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/shop")
    public ResponseEntity<List<ShopDto>> getShopsByUserId(@PathVariable Integer id) {
        List<ShopDto> shops = shopService.getShopsByUserId(id);
        return ResponseEntity.ok(shops);
    }

}

