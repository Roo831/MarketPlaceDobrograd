package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.service.OrderService;
import com.poptsov.marketplace.service.ShopService;
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
    private final OrderService orderService;

    /**
     * Создать магазин и прикрепить его к себе
     *
     * @param shopCreateEditDto Данные нового магазина
     * @return ShopReadDto
     */

    @PostMapping("/create")
    public ResponseEntity<ShopReadDto> createShop(@Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {
        ShopReadDto shopDto = shopService.createShop(shopCreateEditDto);
        return ResponseEntity.ok(shopDto);
    }

    /**
     * Получить свой магазин
     *
     * @return ShopReadDto
     */

    @GetMapping("/myShop")
    public ResponseEntity<ShopReadDto> getMyShop() {
        ShopReadDto shopDto = shopService.getMyShop();
        return ResponseEntity.ok(shopDto);
    }

    /**
     * Редактировать данные своего магазина
     *
     * @param shopCreateEditDto Вносимые изменения
     * @return ShopReadDto
     */

    @PatchMapping("myShop/edit")
    public ResponseEntity<ShopReadDto> editMyShop(@Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {

        ShopReadDto updatedShopDto = shopService.editMyShop(shopCreateEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    /**
     * Удалить свой магазин
     *
     * @return true/false
     */

    @DeleteMapping("myShop/delete")
    public ResponseEntity<Void> deleteMyShop() {
        return shopService.deleteMyShop() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Получить все заказы своего магазина
     *
     * @return List<OrderReadDto>
     */

    @GetMapping("myShop/orders")
    public ResponseEntity<List<OrderReadDto>> getMyShopOrders() {
        List<OrderReadDto> orders = orderService.getOrdersOfMyShop();
        return ResponseEntity.ok(orders);
    }

    /**
     * Получить магазин
     *
     * @param id Идентификатор магазина
     * @return ShopReadDto
     */

    @GetMapping("/{id}")
    public ResponseEntity<ShopReadDto> getShopById(@PathVariable Integer id) {
        ShopReadDto shopDto = shopService.getShopById(id);
        return ResponseEntity.ok(shopDto);
    }

    /**
     * Получить список активных магазинов
     *
     * @return List<ShopReadDto>
     */

    @GetMapping("/active")
    public ResponseEntity<List<ShopReadDto>> getActiveShops() {
        List<ShopReadDto> shops = shopService.getActiveShops();
        return ResponseEntity.ok(shops);
    }

    /**
     * Изменить статус своего магазина
     *
     * @param shopEditStatusDto Статус магазина shopEditStatusDto
     * @return ShopReadDto
     */

    @PatchMapping("myShop/editStatus")
    public ResponseEntity<ShopReadDto> switchActiveStatusOfMyShop(@Validated @RequestBody ShopEditStatusDto shopEditStatusDto) {
       ShopReadDto shop = shopService.switchActiveStatusOfMyShop(shopEditStatusDto);
        return ResponseEntity.ok(shop);
    }

}
