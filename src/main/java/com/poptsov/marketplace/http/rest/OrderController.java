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
     * Создать заказ и прикрепить его к пользователю-заказчику,
     * затем прикрепить его к магазину, откуда пользователь-заказчик совершает покупку.
     * Флаг заказа при его создании = "На рассмотрении/Pending"
     *
     * @param userId Идентификатор пользователя
     * @param shopId Идентификатор магазина
     * @param orderCreateDto данные магазина
     * @return OrderReadDto
     */

    @PostMapping("/create")
    public ResponseEntity<OrderReadDto> createOrder(@RequestParam Integer userId, @RequestParam Integer shopId, @Validated @RequestBody OrderCreateDto orderCreateDto) {
        OrderReadDto orderReadDto = orderService.createOrder(userId, shopId, orderCreateDto);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Получить заказ по его идентификатору
     *
     * @param id Идентификатор заказа, данные магазина ShopCreateDto
     * @return OrderReadDto
     */

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDto> getOrderById(@PathVariable Integer id) {
        OrderReadDto orderReadDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Удалить заказ по его идентификатору
     *
     * @param id Идентификатор заказа,
     * @return ShopReadDto
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Integer id) {
        boolean isDeleted = orderService.deleteOrder(id);
        return ResponseEntity.ok(isDeleted);
    }

    /**
     * Изменить статус заказа
     *
     * @param id Идентификатор заказа,
     * @return OrderReadDto
     */

    @PatchMapping("/{id}")
    public ResponseEntity<OrderReadDto>editOrderStatus(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        OrderReadDto orderReadDto = orderService.editOrderStatus(id, orderEditStatusDto);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Получить заказчика по идентификатору заказа
     *
     * @param id Идентификатор заказа,
     * @return OrderReadDto
     */

    @GetMapping("/{id}/getOwner")
    public ResponseEntity<UserReadDto> getOwnerByOrderId(@PathVariable Integer id) {
        UserReadDto ownerDto = userService.getOwnerByOrderId(id);
        return ResponseEntity.ok(ownerDto);
    }

    /**
     * Получить магазин по идентификатору заказа
     *
     * @param id Идентификатор заказа,
     * @return ShopReadDto
     */

    @GetMapping("/{id}/getShop")
    public ResponseEntity<ShopReadDto> getShopByOrderId(@PathVariable Integer id) {
        ShopReadDto shopReadDto = shopService.getShopByOrderId(id);
        return ResponseEntity.ok(shopReadDto);
    }
}