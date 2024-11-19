package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.exceptions.ShopGetException;
import com.poptsov.marketplace.exceptions.UserGetException;
import com.poptsov.marketplace.mapper.ShopReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final ShopReadMapper shopReadMapper;


    public ShopReadDto createShop(Integer id, ShopCreateDto shopCreateDto) {
        return null;
    }

    public ShopReadDto getShopById(Integer id) {
        return null;
    }

    public ShopReadDto editShop(Integer id, ShopEditDto shopEditDto) {
        return null;
    }

    public boolean deleteShop(Integer id) {
        return false;
    }

    public List<ShopReadDto> getAllShops() {
        return null;
    }

    public List<ShopReadDto> getShopsByUserId(Integer id) {
        return null;
    }

    public ShopReadDto getShopByOrderId(Integer id) {
        return null;
    }

    public List<ShopReadDto> getActiveShops() {
        List<Shop> shops = shopRepository.findByActiveTrue(); // Получаем список пользователей

        if (shops.isEmpty()) {
            throw new ShopGetException("No shops found"); // Выбрасываем исключение, если список пуст
        }

        return shops.stream()
                .map(shopReadMapper::map) // Маппим каждую сущность User в UserReadDto
                .collect(Collectors.toList()); // Собираем в список
    }

    public boolean doPassiveShop(Integer id) {
        return false;
    }

    public boolean doActiveShop(Integer id) {
        return false;
    }
}
