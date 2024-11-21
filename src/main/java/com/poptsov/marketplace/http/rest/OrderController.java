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
        // TODO: проверка владельца (Пользователь может создать заказ только себе, но для любого магазина)
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
        // TODO: проверка владельца (Проверка не нужна, так как реализовать сложно, затрата ресурсов большая, а информация бесполезна)
        OrderReadDto orderReadDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderReadDto);
    }

    /**
     * Изменить статус заказа
     *
     * @param id Идентификатор заказа,
     * @return OrderReadDto
     */

    @PatchMapping("/{id}")
    public ResponseEntity<OrderReadDto>editOrderStatus(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        // TODO: проверка владельца (Пользователь может редактировать только свой заказ или заказ своего магазина)
        OrderReadDto orderReadDto = orderService.editOrderStatus(id, orderEditStatusDto);
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
        // TODO: проверка владельца (Пользователь может редактировать только свою страницу)
        boolean isDeleted = orderService.deleteOrder(id);
        return ResponseEntity.ok(isDeleted);
    }

    /**
     * Получить заказчика по идентификатору заказа
     *
     * @param id Идентификатор заказа,
     * @return OrderReadDto
     */

    @GetMapping("/{id}/owner")
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

    @GetMapping("/{id}/shop")
    public ResponseEntity<ShopReadDto> getShopByOrderId(@PathVariable Integer id) {
        ShopReadDto shopReadDto = shopService.getShopByOrderId(id);
        return ResponseEntity.ok(shopReadDto);
    }
}