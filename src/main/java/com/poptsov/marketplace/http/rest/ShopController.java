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
    public ResponseEntity<ShopReadDto> createShop(@PathVariable Integer id, @Validated @RequestBody ShopCreateDto shopCreateDto) {
        ShopReadDto shopDto = shopService.createShop(id, shopCreateDto);
        return ResponseEntity.ok(shopDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopReadDto> getShopById(@PathVariable Integer id) {
        ShopReadDto shopDto = shopService.getShopById(id);
        return ResponseEntity.ok(shopDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShopReadDto> editShop(@PathVariable Integer id, @Validated @RequestBody ShopEditDto shopEditDto) {
        ShopReadDto updatedShopDto = shopService.editShop(id, shopEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteShop(@PathVariable Integer id) {
        boolean isDeleted = shopService.deleteShop(id);
        return ResponseEntity.ok(isDeleted);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getShopOrders(@PathVariable Integer id) {
        List<OrderReadDto> orders = orderService.getOrdersByShopId(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<ShopReadDto>> getAllShops() {
        List<ShopReadDto> shops = shopService.getAllShops();
        return ResponseEntity.ok(shops);
    }

//    @GetMapping("/{id}/findOwner")
//    public ResponseEntity<UserReadDto> getOwnerByShopId(@PathVariable Integer id) {
//        UserReadDto ownerDto = userService.getOwnerByShopId(id);
//        return ResponseEntity.ok(ownerDto);
//    }

    @GetMapping("/active")
    public ResponseEntity<List<ShopReadDto>> getActiveShops() {
        List<ShopReadDto> shops = shopService.getActiveShops();
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
