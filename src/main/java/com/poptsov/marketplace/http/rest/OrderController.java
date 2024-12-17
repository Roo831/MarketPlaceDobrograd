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


    /**
     * Создать свой заказ и прикрепить к магазину
     * Флаг заказа при его создании = "На рассмотрении/Pending"
     *
     * @param shopId Идентификатор магазина
     * @param orderCreateDto данные магазина
     * @return OrderReadDto
     */

    @PostMapping("/create")
    public ResponseEntity<OrderReadDto> create(@RequestParam Integer shopId, @Validated @RequestBody OrderCreateDto orderCreateDto) {
        OrderReadDto orderReadDto = orderService.create(shopId, orderCreateDto);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Получить заказ по его идентификатору
     *
     * @param id Идентификатор заказа, данные магазина ShopCreateDto
     * @return OrderReadDto
     */

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDto> findById(@PathVariable Integer id) {
        OrderReadDto orderReadDto = orderService.findById(id);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Изменить статус заказа
     *
     * @param id Идентификатор заказа
     * @param orderEditStatusDto статус during или completed
     * @return OrderReadDto
     */

    @PatchMapping("/{id}/editStatus")
    public ResponseEntity<OrderReadDto>editStatus(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        OrderReadDto orderReadDto = orderService.update(id, orderEditStatusDto);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Удалить заказ по его идентификатору
     * Разрешено только себе или владельцу магазина
     *
     * @param id Идентификатор заказа,
     * @return ShopReadDto
     */

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        boolean isDeleted = orderService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

    /**
     * Получить владельца заказа
     *
     * @param id Идентификатор заказа,
     * @return OrderReadDto
     */

    @GetMapping("/{id}/owner")
    public ResponseEntity<UserReadDto> findOwnerByOrderId(@PathVariable Integer id) {
        UserReadDto ownerDto = userService.findOwnerByOrderId(id);
        return ResponseEntity.ok(ownerDto);
    }

    /**
     * Получить магазин по идентификатору заказа
     *
     * @param id Идентификатор заказа,
     * @return ShopReadDto
     */

    @GetMapping("/{id}/shop")
    public ResponseEntity<ShopReadDto> findShopByOrderId(@PathVariable Integer id) {
        ShopReadDto shopReadDto = shopService.getShopByOrderId(id);
        return ResponseEntity.ok(shopReadDto);
    }
}