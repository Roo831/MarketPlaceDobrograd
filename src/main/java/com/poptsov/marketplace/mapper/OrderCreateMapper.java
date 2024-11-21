package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Status;
import com.poptsov.marketplace.dto.OrderCreateDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderCreateMapper implements Mapper<OrderCreateDto, Order> {

    @Override
    public Order map(OrderCreateDto object) {
        return Order.builder()
                .createdAt(new Date())
                .status(Status.pending)
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .build();
    }
}
