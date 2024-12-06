package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class ShopEditMapperTest {

    @InjectMocks
    ShopEditMapper shopEditMapper;

    Shop shop;
    Shop shopToUpdate;
    Integer id;
    ShopEditDto shopEditDto;
    ShopEditStatusDto shopEditStatusDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shop = MockEntityUtil.getTestShop();
        shopToUpdate = MockEntityUtil.getTestShop();
        shopToUpdate.setName("ShopToUpdate");

        shopEditDto = ShopEditDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();

        shopEditStatusDto = ShopEditStatusDto.builder()
                .active(shop.isActive())
                .build();

        id = shop.getId();
    }


    @Test
    void defaultMap() {
        Shop result = shopEditMapper.map(id, shopEditDto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(shopEditDto.getName(), result.getName());
        assertEquals(shopEditDto.getAddress(), result.getAddress());
        assertEquals(shopEditDto.getSpecialization(), result.getSpecialization());
        assertEquals(shopEditDto.getDescription(), result.getDescription());

    }

    @Test
    void editStatusMap() {

        Shop result = shopEditMapper.map(id, shopEditStatusDto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(shopEditStatusDto.isActive(), shopEditStatusDto.isActive());
    }

    @Test
    void enrichEntityMapTest() {

        shopEditMapper.map(shopToUpdate, shopEditDto);

        assertNotNull(shopToUpdate);

        assertEquals(shop.getName(), shopEditDto.getName());
        assertEquals(shop.getAddress(), shopEditDto.getAddress());
        assertEquals(shop.getSpecialization(), shopEditDto.getSpecialization());
        assertEquals(shop.getDescription(), shopEditDto.getDescription());


    }
}