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
     * Редактировать данные своего магазина
     *
     * @param id, Идентификатор магазина
     * @param shopCreateEditDto Вносимые изменения
     * @return ShopReadDto
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ShopReadDto> editShop(@PathVariable Integer id, @Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {

        ShopReadDto updatedShopDto = shopService.editShop(id, shopCreateEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    /**
     * Удалить свой магазин
     *
     * @param id Идентификатор магазина
     * @return true/false
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id) {
        return shopService.deleteShop(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Получить все заказы своего магазина
     *
     * @param id Идентификатор магазина
     * @return List<OrderReadDto>
     */

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderReadDto>> getShopOrders(@PathVariable Integer id) {
        List<OrderReadDto> orders = orderService.getOrdersByShopId(id);
        return ResponseEntity.ok(orders);
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
     * @param id Идентификатор магазина,
     * @param shopEditStatusDto Статус магазина shopEditStatusDto
     * @return ShopReadDto
     */

    @PatchMapping("/{id}/status")
    public ResponseEntity<ShopReadDto> switchActiveStatus(@PathVariable Integer id, @Validated @RequestBody ShopEditStatusDto shopEditStatusDto) {
       ShopReadDto shop = shopService.switchActiveStatus(id, shopEditStatusDto);
        return ResponseEntity.ok(shop);
    }

}
