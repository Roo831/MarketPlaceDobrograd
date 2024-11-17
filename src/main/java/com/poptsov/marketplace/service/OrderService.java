package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public List<OrderReadDto> getOrdersByShopId(Integer id) {
    }

    public List<OrderReadDto> getOrdersByUserId(Integer id) {
    }

    public OrderReadDto getOrderById(Integer orderId) {
    }

    public OrderReadDto createOrder(Integer userId, Integer shopId, OrderCreateDto orderCreateDto) {
    }

    public boolean deleteOrder(Integer id) {
    }

    public OrderReadDto setOrderStatusToProcessing(Integer id, OrderEditStatusDto orderEditStatusDto) {
    }

    public OrderReadDto setOrderStatusToCompleted(Integer id, OrderEditStatusDto orderEditStatusDto) {
        return null;
    }
}
