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
        // TODO: проверка владельца (Пользователь может создать только свой магазин)
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
        // TODO: проверка владельца (Пользователь может редактировать только свою страницу)
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
        // TODO: проверка владельца (Только хозяин магазина может его удалить)
        return shopService.deleteShop(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Получить все заказы магазина
     *
     * @param id Идентификатор магазина
     * @return List<OrderReadDto>
     */

    @GetMapping("/{id}/orders")
    // TODO: проверка владельца (Только хозяин магазина может получить его заказы)
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
        // TODO: проверка владельца (Только владелец магазина может менять статус своего магазина)
       ShopReadDto shop = shopService.switchActiveStatus(id, shopEditStatusDto);
        return ResponseEntity.ok(shop);
    }

}
