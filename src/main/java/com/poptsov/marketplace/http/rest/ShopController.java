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
@CrossOrigin
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final OrderService orderService;

    /**
     * Создать магазин и прикрепить его к себе
     *
     * @param shopCreateDto Данные нового магазина
     * @return ShopReadDto
     */

    @PostMapping("/create")
    public ResponseEntity<ShopReadDto> create(@Validated @RequestBody ShopCreateDto shopCreateDto) {
        ShopReadDto shopDto = shopService.create(shopCreateDto);
        return ResponseEntity.ok(shopDto);
    }

    /**
     * Получить свой магазин
     *
     * @return ShopReadDto
     */

    @GetMapping("/myShop")
    public ResponseEntity<ShopReadDto> getMyShop() {
        ShopReadDto shopDto = shopService.findMyShop();
        return ResponseEntity.ok(shopDto);
    }

    /**
     * Редактировать данные своего магазина
     *
     * @param shopEditDto Вносимые изменения
     * @return ShopReadDto
     */

    @PatchMapping("myShop/edit")
    public ResponseEntity<ShopReadDto> edit(@Validated @RequestBody ShopEditDto shopEditDto) {

        ShopReadDto updatedShopDto = shopService.update(shopEditDto);
        return ResponseEntity.ok(updatedShopDto);
    }

    /**
     * Удалить свой магазин
     *
     * @return true/false
     */

    @DeleteMapping("myShop/delete")
    public ResponseEntity<Boolean> deleteMyShop() {
        boolean isDeleted =  shopService.deleteMyShop();
         return ResponseEntity.ok(isDeleted);
    }

    /**
     * Получить все заказы своего магазина
     *
     * @return List<OrderReadDto>
     */

    @GetMapping("myShop/orders")
    public ResponseEntity<List<OrderReadDto>> getMyShopOrders() {
        List<OrderReadDto> orders = orderService.findOrdersOfMyShop();
        return ResponseEntity.ok(orders);
    }

    /**
     * Получить магазин
     *
     * @param id Идентификатор магазина
     * @return ShopReadDto
     */

    @GetMapping("/{id}")
    public ResponseEntity<ShopReadDto> findById(@PathVariable Integer id) {
        ShopReadDto shopDto = shopService.findById(id);
        return ResponseEntity.ok(shopDto);
    }

    /**
     * Получить список активных магазинов
     *
     * @return List<ShopReadDto>
     */

    @GetMapping("/active")
    public ResponseEntity<List<ShopReadDto>> getActiveShops() {
        List<ShopReadDto> shops = shopService.findActiveShops();
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
