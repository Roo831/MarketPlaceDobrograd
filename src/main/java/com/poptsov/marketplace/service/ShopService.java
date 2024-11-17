package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    public ShopReadDto createShop(Integer id, ShopCreateDto shopCreateDto) {
    }

    public ShopReadDto getShopById(Integer id) {
    }

    public ShopReadDto editShop(Integer id, ShopEditDto shopEditDto) {
    }

    public boolean deleteShop(Integer id) {
    }

    public List<ShopReadDto> getAllShops() {
    }

    public List<ShopReadDto> getShopsByUserId(Integer id) {
    }

    public ShopReadDto getShopByOrderId(Integer id) {
        return null;
    }

    public List<ShopReadDto> getActiveShops() {
        return null;
    }

    public boolean doPassiveShop(Integer id) {
    }

    public boolean doActiveShop(Integer id) {
    }
}
