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
    public ResponseEntity<OrderDto> createOrder(@PathVariable Integer userId, @PathVariable Integer shopId, @Validated @RequestBody OrderCreateDto orderCreateDto) {
        OrderDto orderDto = orderService.createOrder(userId, shopId, orderCreateDto);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        OrderDto orderDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Integer id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return ResponseEntity.ok(isDeleted);
    }

    @PatchMapping("/{id}/during")
    public ResponseEntity<OrderDto> setOrderToProcessing(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        OrderDto orderDto = orderService.setOrderStatusToProcessing(id, orderEditStatusDto);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/{id}/completed")
    public ResponseEntity<OrderDto> setOrderToCompleted(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        OrderDto orderDto = orderService.setOrderStatusToCompleted(id, orderEditStatusDto);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{id}/getOwner")
    public ResponseEntity<UserDto> getOwnerByOrderId(@PathVariable Integer id) {
        UserDto ownerDto = userService.getOwnerByOrderId(id);
        return ResponseEntity.ok(ownerDto);
    }

    @GetMapping("/{id}/getShop")
    public ResponseEntity<ShopDto> getShopByOrderId(@PathVariable Integer id) {
        ShopDto shopDto = shopService.getShopByOrderId(id);
        return ResponseEntity.ok(shopDto);
    }
}