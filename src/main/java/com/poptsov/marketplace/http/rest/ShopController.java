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
     * Создать магазин и прикрепить его к пользователю
     *
     * @param userId Идентификатор пользователя,
     * @param shopCreateEditDto Данные нового магазина
     * @return ShopReadDto
     */

    @PostMapping("/create")
    public ResponseEntity<ShopReadDto> createShop(@RequestParam Integer userId, @Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {
        ShopReadDto shopDto = shopService.createShop(userId, shopCreateEditDto);
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
     * Редактировать данные магазина
     *
     * @param id, Идентификатор магазина
     * @param shopCreateEditDto Вносимые изменения
     * @return ShopReadDto
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ShopReadDto> editShop(@PathVariable Integer id, @Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {
        System.out.println("Received request to edit shop with ID: " + id + " and DTO: " + shopCreateEditDto);
        ShopReadDto updatedShopDto = shopService.editShop(id, shopCreateEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    /**
     * Удалить магазин
     *
     * @param id Идентификатор магазина
     * @return true/false
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id) {
        if(shopService.deleteShop(id))
        return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Получить все заказы магазина
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
     * Изменить статус магазина
     *
     * @param id Идентификатор магазина,
     * @param shopEditStatusDto Статус магазина shopEditStatusDto
     * @return булево значение, возвращает текущий статус
     */

    @PatchMapping("/{id}/status")
    public ResponseEntity<ShopReadDto> switchActiveStatus(@PathVariable Integer id, @Validated @RequestBody ShopEditStatusDto shopEditStatusDto) {
       ShopReadDto shop = shopService.switchActiveStatus(id, shopEditStatusDto);
        return ResponseEntity.ok(shop);
    }

}
