package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.exceptions.EntityGetException;
import com.poptsov.marketplace.exceptions.EntityNotFoundException;
import com.poptsov.marketplace.mapper.ShopCreateMapper;
import com.poptsov.marketplace.mapper.ShopEditMapper;
import com.poptsov.marketplace.mapper.ShopReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopService {
    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final ShopReadMapper shopReadMapper;
    private final ShopEditMapper shopEditMapper;
    private final ShopCreateMapper shopCreateMapper;
    private final UserService userService;

    // CRUD start

    @Transactional
    public ShopReadDto create(ShopCreateDto shopCreateDto) {

        User currentUser = userService.findCurrentUser();

        if (currentUser.getShop() != null && currentUser.getShop().getId() != null) {
            throw new EntityGetException("Shop already exists");
        }

        Shop shop = shopCreateMapper.map(shopCreateDto);
        shop.setUser(currentUser);

        Shop savedShop;

        try {
            savedShop = shopRepository.save(shop);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage();
            String address = shopCreateDto.getAddress();
            String name = shopCreateDto.getName();
            checkUniqueData(address, message);
            checkUniqueData(name, message);
            throw e;
        }
        return shopReadMapper.map(savedShop);
    }

    @Transactional
    public ShopReadDto update(ShopEditDto shopEditDto) {

        User currentUser = userService.findCurrentUser();
        Shop shopToUpdate = currentUser.getShop();

        if (shopToUpdate == null) {
            throw new EntityNotFoundException("Shop not found");
        }

        String address = shopEditDto.getAddress();
        String name = shopEditDto.getName();
        if (shopRepository.existsByName(name))
            throw new EntityGetException(name + " already exists");
        if (shopRepository.existsByAddress(address))
            throw new EntityGetException(address + " already exists");

        shopEditMapper.map(shopToUpdate, shopEditDto);

        Shop updatedShop;
        updatedShop = shopRepository.save(shopToUpdate);

        return shopReadMapper.map(updatedShop);
    }


    public ShopReadDto findById(Integer id) {
        return shopReadMapper.map(shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shop not found with id: " + id)));
    }


    @Transactional()
    public boolean deleteMyShop() {


        User currentUser = userService.findCurrentUser();
        Shop shopToDelete = currentUser.getShop();
        if (shopToDelete == null) {
            throw new EntityNotFoundException("You have no Shop");
        }
        shopRepository.deleteShopById(shopToDelete.getId());
        return true;
    }
    // CRUD end

    public List<ShopReadDto> findActiveShops() {

        return shopRepository.findByActiveTrue()
                .stream()
                .map(shopReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShopReadDto switchActiveStatusOfMyShop(ShopEditStatusDto shopEditStatusDto) {

        User user = userService.findCurrentUser();
        Shop shop = user.getShop();

        if (shop == null) {
            throw new EntityNotFoundException("You have no Shop");
        }

        shop.setActive(shopEditStatusDto.isActive());
        return shopReadMapper.map(shopRepository.save(shop));
    }

    public ShopReadDto findMyShop() {
        Shop shop = userService.findCurrentUser().getShop();
        if (shop == null) {
            throw new EntityNotFoundException("Shop not found");
        }
        return shopReadMapper.map(shop);
    }

    public ShopReadDto getShopByOrderId(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));
        Shop shop = order.getShop();

        if (shop == null) {
            throw new EntityNotFoundException("Shop of order with id " + orderId + "not found");
        }

        return shopReadMapper.map(shop);

    }

    private void checkUniqueData(String wordToFind, String message) { // По невыясненным причинам метод не работает в update методе

        String regex = "\\b" + Pattern.quote(wordToFind) + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(message).find()) {
            throw new EntityGetException(wordToFind + " already exists");
        }
    }
}
