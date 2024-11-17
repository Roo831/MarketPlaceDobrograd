package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    public ShopDto createShop(Integer id, ShopCreateDto shopCreateDto) {
    }

    public ShopDto getShopById(Integer id) {
    }

    public ShopDto editShop(Integer id, ShopEditDto shopEditDto) {
    }

    public boolean deleteShop(Integer id) {
    }

    public List<ShopDto> getAllShops() {
    }

    public List<ShopDto> getShopsByUserId(Integer id) {
    }

    public ShopDto getShopByOrderId(Integer id) {
        return null;
    }

    public List<ShopDto> getActiveShops() {
    }

    public boolean doPassiveShop(Integer id) {
    }

    public boolean doActiveShop(Integer id) {
    }
}
