package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ShopCreateMapperTest {

    @InjectMocks
    private ShopCreateMapper mapper;

    private Shop shop;
    private ShopCreateDto shopCreateDto;
    private Integer id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shop = MockEntityUtil.getTestShop();
        shopCreateDto = ShopCreateDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();
    }

    @Test
    void map() {

        Shop result = mapper.map(shopCreateDto);

        assertNotNull(result);
        assertEquals(shop.getName(), result.getName());
        assertEquals(shop.getAddress(), result.getAddress());
        assertEquals(shop.getSpecialization(), result.getSpecialization());
        assertEquals(shop.getDescription(), result.getDescription());


    }

}