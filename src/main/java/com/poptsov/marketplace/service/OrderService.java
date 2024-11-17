package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public List<OrderDto> getOrdersByShopId(Integer id) {
    }

    public List<OrderDto> getOrdersByUserId(Integer id) {
    }

    public OrderDto getOrderById(Integer orderId) {
    }

    public OrderDto createOrder(Integer userId, Integer shopId, OrderCreateDto orderCreateDto) {
    }

    public boolean deleteOrder(Integer id) {
    }

    public OrderDto setOrderStatusToProcessing(Integer id, OrderEditStatusDto orderEditStatusDto) {
    }

    public OrderDto setOrderStatusToCompleted(Integer id, OrderEditStatusDto orderEditStatusDto) {
        return null;
    }
}
