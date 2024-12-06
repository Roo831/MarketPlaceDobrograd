package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import com.poptsov.marketplace.exceptions.AuthorizationException;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.exceptions.EntityNotFoundException;
import com.poptsov.marketplace.mapper.OrderCreateMapper;
import com.poptsov.marketplace.mapper.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateMapper orderCreateMapper;
    private final UserService userService;


    // CRUD start

    public OrderReadDto findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderReadMapper::map).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Transactional
    public OrderReadDto create(Integer shopId, OrderCreateDto orderCreateDto) {

        User user = userService.findCurrentUser();
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + shopId));

        Order order = orderCreateMapper.map(orderCreateDto);
        order.setUser(user);
        order.setShop(shop);
        Order savedOrder = orderRepository.save(order);

        return orderReadMapper.map(savedOrder);
    }

    @Transactional
    public OrderReadDto update(Integer id, OrderEditStatusDto orderEditStatusDto) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Integer shopOwnerId = order.getShop().getUser().getId();
        if (!Objects.equals(userService.findCurrentUser().getId(), shopOwnerId))
            throw new AuthorizationException("Owner of shop id does not match with your id");

        order.setStatus(orderEditStatusDto.getStatus());
        orderRepository.save(order);
        return orderReadMapper.map(order);
    }

    @Transactional
    public boolean delete(Integer id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Integer ownerId = order.getUser().getId();
        Integer shopOwnerId = order.getShop().getUser().getId();

        if (!Objects.equals(ownerId, userService.findCurrentUser().getId()) && !Objects.equals(shopOwnerId, userService.findCurrentUser().getId())) {
            throw new AuthorizationException("You cannot to delete this order");
        }
        orderRepository.deleteOrderById(id);
        return true;
    }

    // CRUD end

    public List<OrderReadDto> findOrdersOfMyShop() {
        User currentUser = userService.findCurrentUser();
        Shop shop = currentUser.getShop();

        if (shop == null) {
            throw new EntityNotFoundException("You have no shop");
        }

        return shop.getOrders().stream().map(orderReadMapper::map).collect(Collectors.toList());
    }

    public List<OrderReadDto> findMyOrders() {
        User user = userService.findCurrentUser();
        return user.getOrders().stream().map(orderReadMapper::map).collect(Collectors.toList());
    }


}
