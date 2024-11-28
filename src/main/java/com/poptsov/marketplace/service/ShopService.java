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

        User currentUser = userService.findCurrentUser(); // получить текущего юзера

        if (currentUser.getShop() != null && currentUser.getShop().getId() != null) { // если у него есть магазин
            throw new EntityGetException("Shop already exists"); // выбросить исключение
        }

        Shop shop = shopCreateMapper.map(shopCreateDto); // Создать новый магазин, не занося его в кеш hibernate
        shop.setUser(currentUser); //установить текущего юзера (уже в кеше)

        Shop savedShop;  // создать переменную сохраненного магазина

        try {
            savedShop = shopRepository.save(shop); // попытаться сохранить магазин
        } catch (DataIntegrityViolationException e) { // поймать любые ошибки валидации данных
            String message = e.getMessage(); // получить сообщение из ошибки
            String address = shopCreateDto.getAddress(); // получить адрес из ДТО
            String name = shopCreateDto.getName(); // получить имя из ДТО
            checkUniqueData(address, message); // в случае если в сообщении есть адрес - выбросить исключение
            checkUniqueData(name, message); // в случае если в ошибке есть имя -выбросить исключение
            throw e;
        }
        return shopReadMapper.map(savedShop); // замаппить в дто
    }

    @Transactional
    public ShopReadDto update(ShopEditDto shopCreateEditDto) {

        User currentUser = userService.findCurrentUser(); //получить текущего пользователя
        Shop shopToUpdate = currentUser.getShop(); // получить его магазин

        if(shopToUpdate == null) { // если магазина нет
            throw new EntityGetException("Shop not found"); // выбросить исключение
        }

        shopEditMapper.map(shopToUpdate, shopCreateEditDto); // обогатить текущий магазин новыми данными

        Shop updatedShop; // создать переменную обновленного магазина
        try {
            updatedShop = shopRepository.save(shopToUpdate); // попытаться сохранить магазин в переменную

        } catch (DataIntegrityViolationException e) { // поймать любые ошибки валидации данных
            String message = e.getMessage(); // получить сообщение из ошибки
            String address = shopCreateEditDto.getAddress(); // получить адрес из ДТО
            String name = shopCreateEditDto.getName(); // получить имя из ДТО
            checkUniqueData(address, message); // в случае если в сообщении есть адрес - выбросить исключение
            checkUniqueData(name, message); // в случае если в ошибке есть имя - выбросить исключение
            throw e;
        }
        return shopReadMapper.map(updatedShop); // замаппить в дто
    }


    public ShopReadDto findById(Integer id) {
        return shopReadMapper.map(shopRepository.findById(id).orElseThrow(() -> new EntityGetException("Shop not found with id: " + id)));
    }


    @Transactional()
    public boolean deleteMyShop() {


        User currentUser = userService.findCurrentUser();
        Shop shopToDelete = currentUser.getShop();
        if (shopToDelete == null) {
            return false;
        }
        shopRepository.deleteShopById(shopToDelete.getId());

        return true;
    }

    // CRUD end

    public List<ShopReadDto> findActiveShops() {

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

        User user = userService.findCurrentUser();
        Shop shop = user.getShop();

        if(shop == null) {
            throw new EntityGetException("No shop found");
        }

        shop.setActive(shopEditStatusDto.isActive());
        return shopReadMapper.map(shopRepository.save(shop));
    }

    public ShopReadDto findMyShop() {
        Shop shop = userService.findCurrentUser().getShop();
        if(shop == null) {
            throw new EntityGetException("Shop not found");
        }
        return shopReadMapper.map(shop);
    }

    public ShopReadDto getShopByOrderId(Integer id) {
        return orderRepository.findById(id)
                .map(Order::getShop)
                .map(shopReadMapper::map)
                .orElseThrow(() -> new EntityGetException("Order not found with id, or shop aren't exists: " + id));
    }

    private void checkUniqueData(String wordToFind, String message){

        String regex = "\\b" + Pattern.quote(wordToFind) + "\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        if(pattern.matcher(message).find()){
            throw new EntityGetException(wordToFind + " already exists");
        }
    }
}
