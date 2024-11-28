package com.poptsov.marketplace.unit;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.mapper.ShopReadMapper;
import com.poptsov.marketplace.service.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {
    private static final Integer SHOP_ID = 1;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ShopReadMapper shopReadMapper;

    @InjectMocks
    private ShopService shopService;

    @Test
    void findById() {
        // Настраиваем поведение мока shopRepository
        Mockito.doReturn(Optional.of(
                        Shop.builder()
                                .id(SHOP_ID)
                                .build()
                ))
                .when(shopRepository).findById(SHOP_ID);

        // Настраиваем поведение мока shopReadMapper
        Mockito.doReturn(ShopReadDto.builder()
                        .id(SHOP_ID)
                        .build())
                .when(shopReadMapper).map(Mockito.any(Shop.class));

        // Вызываем метод findById
        ShopReadDto actualResult = shopService.findById(SHOP_ID);

        // Проверяем, что результат не равен null
        assertNotNull(actualResult);

        // Создаем ожидаемый результат
        ShopReadDto expectedResult = ShopReadDto.builder()
                .id(SHOP_ID)
                .build();

        // Проверяем, что фактический результат соответствует ожидаемому
        assertEquals(expectedResult, actualResult);
    }
}