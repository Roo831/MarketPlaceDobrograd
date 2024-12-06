package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class OrderReadMapperTest {

    @InjectMocks
    private OrderReadMapper orderReadMapper;

    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        order = MockEntityUtil.getTestOrder();
    }

    @Test
    void map() {

       OrderReadDto result = orderReadMapper.map(order);

       assertNotNull(result);
       assertEquals(order.getId(), result.getOrderId());
       assertEquals(order.getName(), result.getName());
       assertEquals(order.getPrice(), result.getPrice());
       assertEquals(order.getStatus(), result.getStatus());
       assertEquals(order.getCreatedAt(), result.getCreatedAt());
       assertEquals(order.getDescription(), result.getDescription());
       assertEquals(order.getUser().getId(), result.getUserId());
       assertEquals(order.getShop().getId(), result.getShopId());
    }

}