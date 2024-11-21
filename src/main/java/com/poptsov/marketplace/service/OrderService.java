package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import com.poptsov.marketplace.exceptions.AuthorizationException;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.mapper.OrderCreateMapper;
import com.poptsov.marketplace.mapper.OrderReadMapper;
import com.poptsov.marketplace.util.AuthorityCheckUtil;
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
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateMapper orderCreateMapper;
    private final UserService userService;

    public List<OrderReadDto> getOrdersOfMyShop() {
       User currentUser = userService.getCurrentUser();
       Shop shop = currentUser.getShop();

       if(shop == null) {
           throw new EntityGetException("You have no shop");
       }

        return shop.getOrders().stream().map(orderReadMapper::map).collect(Collectors.toList());
    }

    public List<OrderReadDto> getMyOrders() {
        User user = userService.getCurrentUser();
       return user.getOrders().stream().map(orderReadMapper::map).collect(Collectors.toList());
    }

    public OrderReadDto getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderReadMapper::map).orElseThrow(() -> new EntityGetException("Order not found"));
    }

    @Transactional
    public OrderReadDto createOrder(Integer shopId, OrderCreateDto orderCreateDto) {

        User user = userService.getCurrentUser();
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new EntityGetException("Shop not found with id: " + shopId));

        Order order = orderCreateMapper.map(orderCreateDto);
        order.setUser(user);
        order.setShop(shop);
        Order savedOrder = orderRepository.save(order);

        return orderReadMapper.map(savedOrder);
    }

    @Transactional
    public boolean deleteOrder(Integer id) {
        Integer ownerId = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found")).getUser().getId();
        Integer shopOwnerId = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found")).getShop().getUser().getId();
        if (!Objects.equals(ownerId, userService.getCurrentUser ().getId()) && !Objects.equals(shopOwnerId, userService.getCurrentUser ().getId())) {
            throw new AuthorizationException("You cannot to delete this order");
        }
        if (orderRepository.existsById(id)) {
            orderRepository.deleteOrderById(id);
            return true;
        } else return false;

    }

    @Transactional
    public OrderReadDto editOrderStatus(Integer id, OrderEditStatusDto orderEditStatusDto) {
        Integer shopOwnerId = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found")).getShop().getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), shopOwnerId);
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityGetException("Order not found"));
        order.setStatus(orderEditStatusDto.getStatus());
        orderRepository.save(order);
        return orderReadMapper.map(order);
    }

}
