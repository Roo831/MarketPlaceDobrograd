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


    /**
     * Получить пользователя по ид
     * @param id Идентификатор пользователя
     * @return UserReadDto
     */

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> getUserById(@PathVariable Integer id) {
        UserReadDto userReadDto = userService.getUserById(id);
        // TODO: проверка владельца (Пользователь может получить доступ только к своей странице)
        return ResponseEntity.ok( userReadDto);
    }

    /**
     * Редактировать пользователя
     * @param id Идентификатор пользователя
     * @param userEditDto Вносимые изменения
     * @return UserReadDto
     */

    @PatchMapping("/{id}")
    public ResponseEntity<UserReadDto> editUser (@PathVariable Integer id, @Validated @RequestBody UserEditDto userEditDto) {
        UserReadDto updatedUserReadDto = userService.updateUser(id, userEditDto);
        // TODO: проверка владельца (Пользователь может редактировать только свою страницу)
        return ResponseEntity.ok(updatedUserReadDto);
    }

    /**
     * Все заказы пользователя
     * @param id Идентификатор пользователя
     * @return List<OrderReadDto>
     */

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getUserOrders(@PathVariable Integer id) {
        List<OrderReadDto> orders = orderService.getOrdersByUserId(id);
        // TODO: проверка владельца (Пользователь может получить доступ только к своим заказам)
        return ResponseEntity.ok(orders);
    }

    /**
     * Магазин по ид владельца
     * @param id Идентификатор пользователя
     * @return ShopReadDto
     */

    @GetMapping("/{id}/shop")
    public ResponseEntity<List<ShopReadDto>> getShopsByUserId(@PathVariable Integer id) {
        List<ShopReadDto> shops = shopService.getShopsByUserId(id);
        // TODO: проверка владельца (Пользователь может получить доступ только к своему магазину)
        return ResponseEntity.ok(shops);
    }

}

