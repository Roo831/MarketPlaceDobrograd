package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopReadDto;
import org.springframework.stereotype.Component;

@Component
public class ShopReadMapper implements Mapper<Shop, ShopReadDto> {

    @Override
    public ShopReadDto map(Shop object) {
        return ShopReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .address(object.getAddress())
                .rating(object.getRating())
                .isActive(object.isActive())
                .specialization(object.getSpecialization())
                .createdAt(object.getCreatedAt())
                .description(object.getDescription())
                .userId(object.getUser().getId())
                .build();
    }
}
