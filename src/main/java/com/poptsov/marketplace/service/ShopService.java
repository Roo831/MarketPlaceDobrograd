package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.database.repository.UserRepository;
import com.poptsov.marketplace.dto.ShopCreateEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.mapper.ShopCreateEditMapper;
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
    private final ShopCreateEditMapper shopCreateEditMapper;

    public List<ShopReadDto> getActiveShops() {

        List<Shop> shops = shopRepository.findByActiveTrue();

        if (shops.isEmpty()) {
            throw new EntityGetException("No shops found");
        }

        return shops.stream()
                .map(shopReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShopReadDto switchActiveStatus(Integer id, ShopEditStatusDto shopEditStatusDto) {
        Shop shopToUpdate = shopRepository.findById(id)
                .orElseThrow(() -> new EntityGetException("Shop not found with id: " + id)); // Получаем магазин в кеш Hibernate, если он существует

        shopToUpdate.setActive(shopEditStatusDto.isActive()); // Устанавливаем статус магазина
        return shopReadMapper.map(shopRepository.save(shopToUpdate)); // сохраняем обратно, завершая транзакцию
    }

    @Transactional
    public ShopReadDto createShop(Integer userId, ShopCreateEditDto shopCreateDto) {
        return userRepository.findById(userId)
                .map(owner -> {
                    Shop shop = shopCreateEditMapper.map(shopCreateDto);
                    shop.setUser(owner);
                    return shopRepository.save(shop);
                })
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityGetException("User not found with id: " + userId));
    }

    public ShopReadDto getShopById(Integer id) {
        return shopReadMapper.map(shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found with id: " + id)));
    }

    @Transactional
    public ShopReadDto editShop(Integer id, ShopCreateEditDto shopCreateEditDto) {
        Shop shopToUpdate = shopRepository.findById(id)
                .orElseThrow(() -> new EntityGetException("Shop not found with id: " + id));

        shopToUpdate.setName(shopCreateEditDto.getName());
        shopToUpdate.setAddress(shopCreateEditDto.getAddress());
        shopToUpdate.setSpecialization(shopCreateEditDto.getSpecialization());
        shopToUpdate.setDescription(shopCreateEditDto.getDescription());

        return shopReadMapper.map(shopRepository.save(shopToUpdate));
    }

    @Transactional(noRollbackFor = Exception.class)
    public boolean deleteShop(Integer id) {
        System.out.println("Attempting to delete shop with id: " + id);
        if (shopRepository.existsById(id)) {
            System.out.println("try to shopRepository.deleteById(id)... " + id);
            shopRepository.deleteShopById(id);
            return true;
        } else return false;
    }

    public ShopReadDto getShopByUserId(Integer id) {
        return userRepository.findUserById(id)
                .map(User::getShop)
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityGetException("User not found with id, or shop aren't exists: " + id));
    }

    public ShopReadDto getShopByOrderId(Integer id) {
        return orderRepository.findById(id)
                .map(Order::getShop)
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Order not found with id, or shop aren't exists: " + id));
    }
}
