package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public List<OrderReadDto> getOrdersByShopId(Integer id) {
        return null;
    }

    public List<OrderReadDto> getOrdersByUserId(Integer id) {
        return null;
    }

    public OrderReadDto getOrderById(Integer orderId) {
        return null;
    }

    public OrderReadDto createOrder(Integer userId, Integer shopId, OrderCreateDto orderCreateDto) {
        return null;
    }

    public boolean deleteOrder(Integer id) {
        return false;
    }

    public OrderReadDto editOrderStatus(Integer id, OrderEditStatusDto orderEditStatusDto) {
        return null;
    }

}
