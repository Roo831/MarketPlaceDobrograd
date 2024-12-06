package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ShopReadMapperTest {

    @InjectMocks
    private ShopReadMapper shopReadMapper;

    private Shop shop;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shop = MockEntityUtil.getTestShop();
    }

    @Test
    void map() {

        ShopReadDto result = shopReadMapper.map(shop);

        assertNotNull(result);
        assertEquals(shop.getId(), result.getId());
        assertEquals(shop.getName(), result.getName());
        assertEquals(shop.getAddress(), result.getAddress());
        assertEquals(shop.getRating(), result.getRating());
        assertEquals(shop.isActive(), result.isActive());
        assertEquals(shop.getSpecialization(), result.getSpecialization());
        assertEquals(shop.getCreatedAt(), result.getCreatedAt());
        assertEquals(shop.getDescription(), result.getDescription());
        assertEquals(shop.getUser().getId(), result.getUserId());
    }
}