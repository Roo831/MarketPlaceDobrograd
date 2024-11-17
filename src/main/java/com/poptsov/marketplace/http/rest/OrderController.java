package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.OrderService;
import com.poptsov.marketplace.service.ShopService;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ShopService shopService;

    @PostMapping("/users/{userId}/shops/{shopId}")
    public ResponseEntity<OrderReadDto> createOrder(@PathVariable Integer userId, @PathVariable Integer shopId, @Validated @RequestBody OrderCreateDto orderCreateDto) {
        OrderReadDto orderReadDto = orderService.createOrder(userId, shopId, orderCreateDto);
        return ResponseEntity.ok(orderReadDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDto> getOrderById(@PathVariable Integer id) {
        OrderReadDto orderReadDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderReadDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Integer id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return ResponseEntity.ok(isDeleted);
    }

    @PatchMapping("/{id}/during")
    public ResponseEntity<OrderReadDto> setOrderToProcessing(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        OrderReadDto orderReadDto = orderService.setOrderStatusToProcessing(id, orderEditStatusDto);
        return ResponseEntity.ok(orderReadDto);
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<OrderReadDto> setOrderToCompleted(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        OrderReadDto orderReadDto = orderService.setOrderStatusToCompleted(id, orderEditStatusDto);
        return ResponseEntity.ok(orderReadDto);
    }

    @GetMapping("/{id}/getOwner")
    public ResponseEntity<UserReadDto> getOwnerByOrderId(@PathVariable Integer id) {
        UserReadDto ownerDto = userService.getOwnerByOrderId(id);
        return ResponseEntity.ok(ownerDto);
    }

    @GetMapping("/{id}/getShop")
    public ResponseEntity<ShopReadDto> getShopByOrderId(@PathVariable Integer id) {
        ShopReadDto shopDto = shopService.getShopByOrderId(id);
        return ResponseEntity.ok(shopDto);
    }
}