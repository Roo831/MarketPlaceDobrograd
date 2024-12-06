package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShopEditMapper  implements Mapper<ShopEditDto, Shop>{


    @Override
    public Shop map(ShopEditDto object) { // вынужденная заглушка для интерфейса
        return null;
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

    public void map(Shop shopToUpdate, ShopEditDto shopEditDto) {
        shopToUpdate.setName(shopEditDto.getName());
        shopToUpdate.setAddress(shopEditDto.getAddress());
        shopToUpdate.setSpecialization(shopEditDto.getSpecialization());
        shopToUpdate.setDescription(shopEditDto.getDescription());
    }

}
