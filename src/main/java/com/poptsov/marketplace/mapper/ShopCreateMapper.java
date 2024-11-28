package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShopCreateMapper implements Mapper<ShopCreateDto, Shop> {
    @Override
    public Shop map(ShopCreateDto object) {
        return Shop.builder()
                .name(object.getName())
                .address(object.getAddress())
                .rating(1)
                .specialization(object.getSpecialization())
                .createdAt(new Date())
                .active(false)
                .description(object.getDescription())
                .build();
    }

    public Shop map(Integer id, ShopCreateDto object) {
        return Shop.builder()
                .id(id)
                .name(object.getName())
                .address(object.getAddress())
                .specialization(object.getSpecialization())
                .description(object.getDescription())
                .build();

    }

}
