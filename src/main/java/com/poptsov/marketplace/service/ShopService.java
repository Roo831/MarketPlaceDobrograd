package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
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
        return null;
    }

    public boolean doPassiveShop(Integer id) {
        return false;
    }

    public boolean doActiveShop(Integer id) {
        return false;
    }
}
