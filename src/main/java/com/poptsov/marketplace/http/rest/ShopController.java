package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.ShopService;
import com.poptsov.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopsController {

    private final ShopService shopService;
    private final UserService userService;


    @PostMapping("/shopCreate/users/{id}")
    public ResponseEntity<ShopDto> createShop(@PathVariable Long id, @RequestBody ShopCreateDto shopCreateDto) {
        ShopDto shopDto = shopService.createShop(id, shopCreateDto);
        return ResponseEntity.ok(shopDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable Long id) {
        ShopDto shopDto = shopService.getShopById(id);
        return ResponseEntity.ok(shopDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShopDto> editShop(@PathVariable Long id, @RequestBody ShopEditDto shopEditDto) {
        ShopDto updatedShopDto = shopService.editShop(id, shopEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteShop(@PathVariable Long id) {
        boolean isDeleted = shopService.deleteShop(id);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getShopOrders(@PathVariable Long id) {
        List<OrderDto> orders = orderService.getOrdersByShopId(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<ShopDto>> getAllShops() {
        List<ShopDto> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

    @GetMapping("/{id}/findOwner")
    public ResponseEntity<UserDto> getOwnerByShopId(@PathVariable Long id) {
        UserDto ownerDto = userService.getOwnerByShopId(id);
        return ResponseEntity.ok(ownerDto);
    }
}
