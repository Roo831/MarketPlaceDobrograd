package com.poptsov.marketplace.http.rest;


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
    public ResponseEntity<List<UserReadDto>> getUsers() {
        List<UserReadDto> usersDto = userService.getAllUsers();
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> getUserById(@PathVariable Integer id) {
        UserReadDto userReadDto = userService.getUserById(id);
        return ResponseEntity.ok(userReadDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserReadDto> editUser (@PathVariable Integer id, @Validated @RequestBody UserEditDto userEditDto) {
        UserReadDto updatedUserReadDto = userService.updateUser(id, userEditDto);
        return ResponseEntity.ok(updatedUserReadDto);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getUserOrders(@PathVariable Integer id) {
        List<OrderReadDto> orders = orderService.getOrdersByUserId(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/shop")
    public ResponseEntity<List<ShopReadDto>> getShopsByUserId(@PathVariable Integer id) {
        List<ShopReadDto> shops = shopService.getShopsByUserId(id);
        return ResponseEntity.ok(shops);
    }

}

