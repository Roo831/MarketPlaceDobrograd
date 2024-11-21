package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;

import com.poptsov.marketplace.service.OrderService;
import com.poptsov.marketplace.service.ShopService;
import com.poptsov.marketplace.service.UserService;
import com.poptsov.marketplace.util.AuthorityCheckUtil;
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
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), id); // TODO: проверка владельца (Пользователь может получить доступ только к своей странице)
        UserReadDto userReadDto = userService.getUserById(id);
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
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), id); // TODO: проверка владельца (Пользователь может редактировать только свою страницу)
        UserReadDto updatedUserReadDto = userService.updateUser(id, userEditDto);
        return ResponseEntity.ok(updatedUserReadDto);
    }

    /**
     * Все заказы пользователя
     * @param id Идентификатор пользователя
     * @return List<OrderReadDto>
     */

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getUserOrders(@PathVariable Integer id) {
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), id);// TODO: проверка владельца (Пользователь может получить доступ только к своим заказам)
        List<OrderReadDto> orders = orderService.getOrdersByUserId(id);
        return ResponseEntity.ok(orders);
    }

    /**
     * Магазин по ид владельца
     * @param id Идентификатор пользователя
     * @return ShopReadDto
     */

    @GetMapping("/{id}/shop")
    public ResponseEntity<ShopReadDto> getShopByUserId(@PathVariable Integer id) {
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), id); // TODO: проверка владельца (Пользователь может получить доступ только к своему магазину)
        ShopReadDto shop = shopService.getShopByUserId(id);
        return ResponseEntity.ok(shop);
    }


}

