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
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final UserService userService;
    private final OrderService orderService;


    @PostMapping("/shopCreate/users/{id}")
    public ResponseEntity<ShopDto> createShop(@PathVariable Integer id, @Validated @RequestBody ShopCreateDto shopCreateDto) {
        ShopDto shopDto = shopService.createShop(id, shopCreateDto);
        return ResponseEntity.ok(shopDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable Integer id) {
        ShopDto shopDto = shopService.getShopById(id);
        return ResponseEntity.ok(shopDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShopDto> editShop(@PathVariable Integer id, @Validated @RequestBody ShopEditDto shopEditDto) {
        ShopDto updatedShopDto = shopService.editShop(id, shopEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteShop(@PathVariable Integer id) {
        boolean isDeleted = shopService.deleteShop(id);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getShopOrders(@PathVariable Integer id) {
        List<OrderDto> orders = orderService.getOrdersByShopId(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<ShopDto>> getAllShops() {
        List<ShopDto> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/{id}/findOwner")
    public ResponseEntity<UserDto> getOwnerByShopId(@PathVariable Integer id) {
        UserDto ownerDto = userService.getOwnerByShopId(id);
        return ResponseEntity.ok(ownerDto);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ShopDto>> getActiveShops() {
        List<ShopDto> shops = shopService.getActiveShops();
        return ResponseEntity.ok(shops);
    }

    @PatchMapping("/{id}/doActive")
    public ResponseEntity<Boolean> doActiveShop(@PathVariable Integer id) {
        boolean isActive = shopService.doActiveShop(id);
        return ResponseEntity.ok(isActive);
    }


    @PatchMapping("/{id}/doPassive")
    public ResponseEntity<Boolean> doPassiveShop(@PathVariable Integer id) {
        boolean isPassive = shopService.doPassiveShop(id);
        return ResponseEntity.ok(isPassive);
    }
}
