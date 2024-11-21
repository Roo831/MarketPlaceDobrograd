package com.poptsov.marketplace.http.rest;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.dto.*;
import com.poptsov.marketplace.exceptions.AuthorizationException;
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

import java.util.Objects;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ShopService shopService;
    private final OrderRepository orderRepository;


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
    @Transactional
    public ResponseEntity<OrderReadDto> createOrder(@RequestParam Integer userId, @RequestParam Integer shopId, @Validated @RequestBody OrderCreateDto orderCreateDto) {
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), userId);// TODO: проверка владельца (Пользователь может создать заказ только себе, но для любого магазина)
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
    @Transactional
    public ResponseEntity<OrderReadDto>editOrderStatus(@PathVariable Integer id, @Validated @RequestBody OrderEditStatusDto orderEditStatusDto) {
        Integer shopOwnerId = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found")).getShop().getUser().getId();
        // TODO: проверка владельца (Пользователь может редактировать только заказ своего магазина)
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), shopOwnerId);
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
    @Transactional
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Integer id) {
        Integer ownerId = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found")).getUser().getId();
        Integer shopOwnerId = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found")).getShop().getUser().getId();
        if (!Objects.equals(ownerId, userService.getCurrentUser ().getId()) && !Objects.equals(shopOwnerId, userService.getCurrentUser ().getId())) {
            throw new AuthorizationException("Вы не можете удалить этот заказ.");
        }
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