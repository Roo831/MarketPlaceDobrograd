package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping("/users/{userId}/shops/{shopId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @PathVariable Long shopId, @RequestBody OrderCreateDto orderCreateDto) {
        // Логика создания заказа
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        // Логика получения заказа по ID
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long id) {
        // Логика удаления заказа
    }

    @PatchMapping("/{id}/during")
    public ResponseEntity<OrderDto> setOrderInProcess(@PathVariable Long id, @RequestBody OrderEditStatusDto orderEditStatusDto) {
        // Логика установки флага "В Обработке"
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<OrderDto> setOrderCompleted(@PathVariable Long id, @RequestBody OrderEditStatusDto orderEditStatusDto) {
        // Логика установки флага "Готов"
    }

    @GetMapping("/{id}/getOwner")
    public ResponseEntity<UserDto> getOrderOwner(@PathVariable Long id) {
        // Логика получения владельца по ID заказа
    }

    @GetMapping("/{id}/getShop")
    public ResponseEntity<ShopDto> getOrderShop(@PathVariable Long id) {
        // Логика получения магазина по ID заказа
    }
}
