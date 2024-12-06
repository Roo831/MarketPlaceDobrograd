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
     * Получить себя
     * @return UserReadDto
     */

    @GetMapping("/me")
    public ResponseEntity<UserReadDto> getMyself() {
        UserReadDto userReadDto = userService.getMyself();
        return ResponseEntity.ok( userReadDto);
    }

    /**
     * Редактировать себя
     * @param userEditDto Вносимые изменения
     * @return UserReadDto
     */

    @PatchMapping("/me/edit")
    public ResponseEntity<UserReadDto> edit (@Validated @RequestBody UserEditDto userEditDto) {
        UserReadDto updatedUserReadDto = userService.update(userEditDto);
        return ResponseEntity.ok(updatedUserReadDto);
    }

    /**
     * Получить свои заказы
     * @return List<OrderReadDto>
     */

    @GetMapping("/me/orders")
    public ResponseEntity<List<OrderReadDto>> findMyOrders() {
        List<OrderReadDto> orders = orderService.findMyOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Получить свой магазин
     * @return ShopReadDto
     */

    @GetMapping("/me/shop")
    public ResponseEntity<ShopReadDto> findMyShop() {
        ShopReadDto shop = shopService.findMyShop();
        return ResponseEntity.ok(shop);
    }

}

