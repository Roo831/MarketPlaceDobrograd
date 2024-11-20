package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.mapper.ShopReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ShopReadMapper shopReadMapper;

    public List<ShopReadDto> getActiveShops() {

        List<Shop> shops = shopRepository.findByActiveTrue();

        if (shops.isEmpty()) {
            throw new EntityGetException("No shops found");
        }

        return shops.stream()
                .map(shopReadMapper::map)
                .collect(Collectors.toList());
    }

    public boolean switchActiveStatus(Integer id, ShopEditStatusDto shopEditStatusDto) {
        return false;
    }

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

    public List<ShopReadDto> getShopsByUserId(Integer id) {
        return null;
    }

    public ShopReadDto getShopByOrderId(Integer id) {
        return null;
    }
}
