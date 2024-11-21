package com.poptsov.marketplace.http.rest;


import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.service.OrderService;
import com.poptsov.marketplace.service.ShopService;
import com.poptsov.marketplace.service.UserService;
import com.poptsov.marketplace.util.AuthorityCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final OrderService orderService;
    private final ShopRepository shopRepository;
    private final UserService userService;

    /**
     * Создать магазин и прикрепить его к пользователю
     *
     * @param userId Идентификатор пользователя,
     * @param shopCreateEditDto Данные нового магазина
     * @return ShopReadDto
     */

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<ShopReadDto> createShop(@RequestParam Integer userId, @Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), userId);// TODO: проверка владельца (Пользователь может создать только свой магазин)
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
    @Transactional
    public ResponseEntity<ShopReadDto> editShop(@PathVariable Integer id, @Validated @RequestBody ShopCreateEditDto shopCreateEditDto) {
        // TODO: проверка владельца (Только хозяин магазина может редактировать его)
       Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId);

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
    @Transactional
    public ResponseEntity<Void> deleteShop(@PathVariable Integer id) {
        Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId); // TODO: проверка владельца (Только хозяин магазина может его удалить)
        return shopService.deleteShop(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Получить все заказы магазина
     *
     * @param id Идентификатор магазина
     * @return List<OrderReadDto>
     */

    @GetMapping("/{id}/orders")
    @Transactional
    public ResponseEntity<List<OrderReadDto>> getShopOrders(@PathVariable Integer id) {
        Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId);
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
    @Transactional
    public ResponseEntity<ShopReadDto> switchActiveStatus(@PathVariable Integer id, @Validated @RequestBody ShopEditStatusDto shopEditStatusDto) {
        Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId); // TODO: проверка владельца (Только владелец магазина может менять статус своего магазина)
       ShopReadDto shop = shopService.switchActiveStatus(id, shopEditStatusDto);
        return ResponseEntity.ok(shop);
    }

}
