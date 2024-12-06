package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
class OrderCreateMapperTest {

    @InjectMocks
    private OrderCreateMapper orderCreateMapper;

    private Order order;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        order = MockEntityUtil.getTestOrder();
    }

    @Test
    void map() {


        OrderCreateDto orderCreateDto = OrderCreateDto.builder()
                .name(order.getName())
                .price(order.getPrice())
                .description(order.getDescription())
                .build();

       Order result = orderCreateMapper.map(orderCreateDto);

       assertNotNull(result);
       assertEquals(order.getName(), result.getName());
       assertEquals(order.getPrice(), result.getPrice());
       assertEquals(order.getDescription(), result.getDescription());



    }
}