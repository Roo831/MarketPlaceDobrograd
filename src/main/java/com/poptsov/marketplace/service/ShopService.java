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
import com.poptsov.marketplace.util.AuthorityCheckUtil;
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
    private final UserService userService;

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

        Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId);

        Shop shopToUpdate = shopRepository.findById(id)
                .orElseThrow(() -> new EntityGetException("Shop not found with id: " + id));

        shopToUpdate.setActive(shopEditStatusDto.isActive());
        return shopReadMapper.map(shopRepository.save(shopToUpdate));
    }

    @Transactional
    public ShopReadDto createShop(ShopCreateEditDto shopCreateDto) {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getShop() != null && currentUser.getShop().getId() != null) {
            throw new EntityGetException("Shop already exists");
        }
        Shop shop = shopCreateEditMapper.map(shopCreateDto);
        shop.setUser(currentUser);
        return shopReadMapper.map(shopRepository.save(shop));
    }

    public ShopReadDto getShopById(Integer id) {
        return shopReadMapper.map(shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found with id: " + id)));
    }

    @Transactional
    public ShopReadDto editShop(Integer id, ShopCreateEditDto shopCreateEditDto) {

        Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId);

        Shop shopToUpdate = shopRepository.findById(id)
                .orElseThrow(() -> new EntityGetException("Shop not found with id: " + id));

        shopToUpdate.setName(shopCreateEditDto.getName());
        shopToUpdate.setAddress(shopCreateEditDto.getAddress());
        shopToUpdate.setSpecialization(shopCreateEditDto.getSpecialization());
        shopToUpdate.setDescription(shopCreateEditDto.getDescription());

        return shopReadMapper.map(shopRepository.save(shopToUpdate));
    }

    @Transactional
    public boolean deleteShop(Integer id) {
        Integer ownerId = shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found")).getUser().getId();
        AuthorityCheckUtil.checkAuthorities(userService.getCurrentUser().getId(), ownerId);
        if (shopRepository.existsById(id)) {
            shopRepository.deleteShopById(id);
            return true;
        } else return false;
    }

    public ShopReadDto getMyShop() {
        return shopReadMapper.map(userService.getCurrentUser().getShop());
    }

    public ShopReadDto getShopByOrderId(Integer id) {
        return orderRepository.findById(id)
                .map(Order::getShop)
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Order not found with id, or shop aren't exists: " + id));
    }
}
