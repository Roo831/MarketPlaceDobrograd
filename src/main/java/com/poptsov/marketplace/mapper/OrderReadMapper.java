package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import org.springframework.stereotype.Component;

@Component
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {



    @Override
    public OrderReadDto map(Order object) {
        return OrderReadDto.builder()
                .orderId(object.getId())
                .createdAt(object.getCreatedAt())
                .status(object.getStatus())
                .name(object.getName())
                .description(object.getDescription())
                .price(object.getPrice())
                .userId(object.getUser().getId())
                .shopId(object.getShop().getId())
                .build();

    }
}
