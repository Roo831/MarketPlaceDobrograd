package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.ShopCreateEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShopCreateEditMapper  implements Mapper<ShopCreateEditDto, Shop>{


    @Override
    public Shop map(ShopCreateEditDto object) {
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

    public Shop map(Integer id, ShopCreateEditDto object) {
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

}
