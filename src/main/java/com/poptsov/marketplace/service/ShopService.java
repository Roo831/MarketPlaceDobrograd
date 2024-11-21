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
    public ShopReadDto switchActiveStatusOfMyShop( ShopEditStatusDto shopEditStatusDto) {

        User user = userService.getCurrentUser();
        Shop shop = user.getShop();

        if(shop == null) {
            throw new EntityGetException("No shop found");
        }

        shop.setActive(shopEditStatusDto.isActive());
        return shopReadMapper.map(shopRepository.save(shop));
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
    public ShopReadDto editMyShop(ShopCreateEditDto shopCreateEditDto) {

       User currentUser = userService.getCurrentUser();

       Shop shopToUpdate = currentUser.getShop();
        if(shopToUpdate == null) {
            throw new EntityGetException("Shop not found");
        }
       shopCreateEditMapper.map(shopCreateEditDto, shopToUpdate);
        return shopReadMapper.map(shopRepository.save(shopToUpdate));
    }

    @Transactional
    public boolean deleteMyShop() {
      User currentUser = userService.getCurrentUser();
      Shop shopToDelete = currentUser.getShop();
      if(shopToDelete == null) {
          return false;
      }
      shopRepository.delete(shopToDelete);
      return true;
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
