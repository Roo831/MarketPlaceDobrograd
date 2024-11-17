package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @PostMapping("/users/{id}")
    public ResponseEntity<ShopDto> createShop(@PathVariable Long id, @RequestBody ShopCreateDto shopCreateDto) {
        // Логика создания магазина и прикрепления его к пользователю
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ShopDto> getShopByUserId(@PathVariable Long id) {
        // Логика получения магазина по ID пользователя
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShopDto> editShop(@PathVariable Long id, @RequestBody ShopEditDto shopEditDto) {
        // Логика редактирования данных магазина
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteShop(@PathVariable Long id) {
        // Логика удаления магазина
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getShopOrders(@PathVariable Long id) {
        // Логика получения всех заказов магазина
    }

    @GetMapping
    public ResponseEntity<List<ShopDto>> getAllShops() {
        // Логика получения списка магазинов
    }

    @GetMapping("/{id}/findOwner")
    public ResponseEntity<UserDto> getShopOwner(@PathVariable Long id) {
        // Логика получения владельца по ID магазина
    }
}