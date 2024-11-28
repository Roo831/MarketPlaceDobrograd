package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShopEditMapper  implements Mapper<ShopEditDto, Shop>{


    @Override
    public Shop map(ShopEditDto object) {
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

    public Shop map(Integer id, ShopEditDto object) {
        return Shop.builder()
                .id(id)
                .name(object.getName())
                .address(object.getAddress())
                .specialization(object.getSpecialization())
                .description(object.getDescription())
                .build();

    }

    public Shop map(Integer id, ShopEditStatusDto object) {
        return Shop.builder()
                .id(id)
                .active(object.isActive())
                .build();

    }

    public void map(Shop shopToUpdate, ShopEditDto shopCreateEditDto) {
        shopToUpdate.setName(shopCreateEditDto.getName());
        shopToUpdate.setAddress(shopCreateEditDto.getAddress());
        shopToUpdate.setSpecialization(shopCreateEditDto.getSpecialization());
        shopToUpdate.setDescription(shopCreateEditDto.getDescription());
    }

}
