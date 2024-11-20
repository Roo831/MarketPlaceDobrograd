package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.dto.UserReadDto;
import com.poptsov.marketplace.dto.UserRoleDto;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    /**
     * Получить список пользователей.
     * @return List<UserReadDto>
     */

    @GetMapping("/users")
    public ResponseEntity<List<UserReadDto>> getUsers() {
        return ResponseEntity.ok( userService.getAllUsers());
    }

    /**
     * Получить пользователя по ид
     * @param id Идентификатор пользователя
     * @return UserReadDto
     */

    @GetMapping("/users/{id}")
    public ResponseEntity<UserReadDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok( userService.getUserById(id));
    }

    /**
     * Изменить роль пользователю
     * @param id Идентификатор пользователя,
     * @param userRoleDto Новая роль
     * @return UserReadDto
     */

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserReadDto> changeRole(@PathVariable Integer id, @Validated @RequestBody UserRoleDto userRoleDto) {
        return ResponseEntity.ok(userService.updateUser(id, userRoleDto));
    }

    /**
     * Получить магазин пользователя
     * @param id Идентификатор пользователя
     * @return ShopReadDto
     */

    @GetMapping("/users/{id}/shop")
    public ResponseEntity<ShopReadDto> getShopByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok( userService.getShopByUserId(id));
    }

    /**
     * Получить список заказов пользователя
     * @param id Идентификатор пользователя
     * @return List<OrderReadDto>
     */

    @GetMapping("/users/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getOrdersByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok( userService.getOrdersByUserId(id));
    }

    /**
     * Получить заказ
     * @param id Идентификатор пользователя
     * @return OrderReadDto
     */

}
