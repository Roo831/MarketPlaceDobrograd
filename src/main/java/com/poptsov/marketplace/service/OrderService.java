package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
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

    public OrderReadDto setOrderStatusToProcessing(Integer id, OrderEditStatusDto orderEditStatusDto) {
        return null;
    }

    public OrderReadDto setOrderStatusToCompleted(Integer id, OrderEditStatusDto orderEditStatusDto) {
        return null;
    }
}
