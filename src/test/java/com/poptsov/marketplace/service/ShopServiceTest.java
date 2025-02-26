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
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ShopServiceTest {

    @InjectMocks
    private ShopServiceImpl shopService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ShopReadMapper shopReadMapper;

    @Mock
    private ShopEditMapper shopEditMapper;

    @Mock
    private ShopCreateMapper shopCreateMapper;

    @Mock
    private UserService userService;

    @Mock
    private Order order;

    @Mock
    private Shop shop;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        shop = MockEntityUtil.getTestShop();
        order = MockEntityUtil.getTestOrder();
        user = MockEntityUtil.getTestUser();
        shop.setUser(user);
        shop.setOrders(List.of(order));
        order.setShop(shop);
        order.setUser(user);
        user.setShop(shop);
        user.setOrders(List.of(order));

    }

    @Test
    void create_get() {

        ShopCreateDto shopCreateDto = ShopCreateDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();

        ShopReadDto expectedResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(shop.getUser().getId())
                .build();

        User userWithoutShop = MockEntityUtil.getTestUser();
        userWithoutShop.setShop(new Shop());

        Shop shopWithoutUser = MockEntityUtil.getTestShop();
        shopWithoutUser.setUser(null);

        when(userService.findCurrentUser()).thenReturn(userWithoutShop);
        when(shopCreateMapper.map(shopCreateDto)).thenReturn(shopWithoutUser);
        when(shopRepository.save(shopWithoutUser)).thenReturn(shopWithoutUser);
        when(shopReadMapper.map(shopWithoutUser)).thenReturn(expectedResult);

        ShopReadDto actualResult = shopService.create(shopCreateDto);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getName(), actualResult.getName());
        assertEquals(expectedResult.getAddress(), actualResult.getAddress());
        assertEquals(expectedResult.getRating(), actualResult.getRating());
        assertEquals(expectedResult.isActive(), actualResult.isActive());
        assertEquals(expectedResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        assertEquals(expectedResult.getUserId(), actualResult.getUserId());
    }

    @Test
    void create_throw_EntityGetException() {

        ShopCreateDto shopCreateDto = ShopCreateDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();

        ShopReadDto expectedResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(user.getId())
                .build();

        when(userService.findCurrentUser()).thenReturn(user);
        when(shopCreateMapper.map(shopCreateDto)).thenReturn(shop);
        when(shopRepository.save(shop)).thenReturn(shop);
        when(shopReadMapper.map(shop)).thenReturn(expectedResult);

        assertThrows(EntityGetException.class, () -> shopService.create(shopCreateDto));

    }

    @Test
    void update() {

        ShopEditDto shopEditDto = ShopEditDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();

        ShopReadDto expectedResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(user.getId())
                .build();
        when(userService.findCurrentUser()).thenReturn(user);
        when(shopRepository.existsByName(shop.getName())).thenReturn(false);
        when(shopRepository.existsByAddress(shop.getAddress())).thenReturn(false);
        when(shopEditMapper.map(any())).thenReturn(shop);
        when(shopRepository.save(shop)).thenReturn(shop);
        when(shopReadMapper.map(shop)).thenReturn(expectedResult);

        ShopReadDto actualResult = shopService.update(shopEditDto);
        assertNotNull(actualResult);
        assertEquals(expectedResult.getId(), actualResult.getId());
        assertEquals(expectedResult.getName(), actualResult.getName());
        assertEquals(expectedResult.getAddress(), actualResult.getAddress());
        assertEquals(expectedResult.getRating(), actualResult.getRating());
        assertEquals(expectedResult.isActive(), actualResult.isActive());
        assertEquals(expectedResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        assertEquals(expectedResult.getUserId(), actualResult.getUserId());

    }

    @Test
    void update_throw_EntityNotFoundException() {

        ShopEditDto shopEditDto = ShopEditDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();

        user.setShop(null);

        when(userService.findCurrentUser()).thenReturn(user);

        assertThrows(EntityNotFoundException.class, () -> shopService.update(shopEditDto));

    }

    @Test
    void update_throw_EntityGetException() {
        ShopEditDto shopEditDto = ShopEditDto.builder()
                .name(shop.getName()) // Используйте имя, которое уже существует
                .address(shop.getAddress()) // Используйте адрес, который уже существует
                .specialization(shop.getSpecialization())
                .description(shop.getDescription())
                .build();
        User otherUser =  User.builder() // Создаем другого пользователя, владельца магазина, адрес и имя которого будут совпадать с shopEditDto
                .id(632) // Ид не равен нашему id
                .build();
        Shop otherShop = Shop.builder()  // Создаем другой магазин, где имя и адрес будут такие же, как и те что пришли из shopEditDto, чтобы получить исключение
                .id(632) // Ид магазина не должен совпадать с нашим
                .name(shop.getName())
                .address(shop.getAddress())
                .user(otherUser)
                .build();
        when(userService.findCurrentUser()).thenReturn(user);
        when(shopRepository.findByName(shop.getName())).thenReturn(Optional.of(otherShop)); // Мок другого магазина
        when(shopRepository.findByAddress(shop.getAddress())).thenReturn(Optional.of(otherShop)); // Мок другого магазина

        assertThrows(EntityGetException.class, () -> shopService.update(shopEditDto));
    }

    @Test
    void findById_get() {

        ShopReadDto exceptResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(shop.getUser().getId())
                .build();

        when(shopRepository.findById(shop.getId())).thenReturn(Optional.ofNullable(shop));
        when(shopReadMapper.map(shop)).thenReturn(exceptResult);

        ShopReadDto actualResult = shopService.findById(shop.getId());

        assertNotNull(actualResult);
        assertEquals(exceptResult.getId(), actualResult.getId());
        assertEquals(exceptResult.getName(), actualResult.getName());
        assertEquals(exceptResult.getAddress(), actualResult.getAddress());
        assertEquals(exceptResult.getRating(), actualResult.getRating());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(exceptResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(exceptResult.getDescription(), actualResult.getDescription());
        assertEquals(exceptResult.getUserId(), actualResult.getUserId());

    }

    @Test
    void findById_throw() {
        when(shopRepository.findById(shop.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> shopService.findById(shop.getId()));
    }

    @Test
    void deleteMyShop_get_true() {

        boolean exceptResult = true;
        when(userService.findCurrentUser()).thenReturn(user);

        boolean actualResult = shopService.deleteMyShop();

        assertEquals(exceptResult, actualResult);

    }

    @Test
    void deleteMyShop_get_false() {

        User userWithoutShop = new User();

        when(userService.findCurrentUser()).thenReturn(userWithoutShop);

        assertThrows(EntityNotFoundException.class, () -> shopService.deleteMyShop());

    }

    @Test
    void findActiveShops_get() {

        shop.setActive(true);

        ShopReadDto exceptResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(shop.getUser().getId())
                .build();

        when(shopRepository.findByActiveTrue()).thenReturn(List.of(shop));
        when(shopReadMapper.map(shop)).thenReturn(exceptResult);

        ShopReadDto actualResult = shopService.findActiveShops().get(0);

        assertNotNull(actualResult);
        assertEquals(exceptResult.getId(), actualResult.getId());
        assertEquals(exceptResult.getName(), actualResult.getName());
        assertEquals(exceptResult.getAddress(), actualResult.getAddress());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(exceptResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getDescription(), actualResult.getDescription());
        assertEquals(exceptResult.getUserId(), actualResult.getUserId());

    }

    @Test
    void findActiveShops_emptyList() {


        when(shopRepository.findByActiveTrue()).thenReturn(Collections.emptyList());
        when(shopReadMapper.map(shop)).thenReturn(null);

        List<ShopReadDto> actualResultList = shopService.findActiveShops();

        assertNotNull(actualResultList);
        assertEquals(0, actualResultList.size());
    }

    @Test
    void switchActiveStatusOfMyShop_get() {

        ShopEditStatusDto shopEditStatusDto = ShopEditStatusDto.builder()
                .active(shop.isActive())
                .build();

        ShopReadDto exceptResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(shop.getUser().getId())
                .build();

        when(userService.findCurrentUser()).thenReturn(user);
        when(shopRepository.save(shop)).thenReturn(shop);
        when(shopReadMapper.map(shop)).thenReturn(exceptResult);

        ShopReadDto actualResult = shopService.switchActiveStatusOfMyShop(shopEditStatusDto);

        assertNotNull(actualResult);
        assertEquals(exceptResult.getId(), actualResult.getId());
        assertEquals(exceptResult.getName(), actualResult.getName());
        assertEquals(exceptResult.getAddress(), actualResult.getAddress());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(exceptResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getDescription(), actualResult.getDescription());
        assertEquals(exceptResult.getUserId(), actualResult.getUserId());
    }

    @Test
    void switchActiveStatusOfMyShop_throw() {
        User userWithoutShop = new User();
        ShopEditStatusDto shopEditStatusDto = ShopEditStatusDto.builder()
                .active(shop.isActive())
                .build();


        when(userService.findCurrentUser()).thenReturn(userWithoutShop);

        assertThrows(EntityNotFoundException.class, () -> shopService.switchActiveStatusOfMyShop(shopEditStatusDto));

    }

    @Test
    void findMyShop_get() {
        when(userService.findCurrentUser()).thenReturn(user);

        ShopReadDto exceptResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(shop.getUser().getId())
                .build();

        when(shopReadMapper.map(shop)).thenReturn(exceptResult);

        ShopReadDto actualResult = shopService.findMyShop();

        assertNotNull(actualResult);
        assertEquals(exceptResult.getId(), actualResult.getId());
        assertEquals(exceptResult.getName(), actualResult.getName());
        assertEquals(exceptResult.getAddress(), actualResult.getAddress());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(exceptResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getDescription(), actualResult.getDescription());
        assertEquals(exceptResult.getUserId(), actualResult.getUserId());

    }

    @Test
    void findMyShop_throw() {

        User userWithoutShop = new User();
        when(userService.findCurrentUser()).thenReturn(userWithoutShop);

        assertThrows(EntityNotFoundException.class, () -> shopService.findMyShop());


    }

    @Test
    void getShopByOrderId_get() {

        ShopReadDto exceptResult = ShopReadDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .isActive(shop.isActive())
                .specialization(shop.getSpecialization())
                .createdAt(shop.getCreatedAt())
                .description(shop.getDescription())
                .userId(shop.getUser().getId())
                .build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(shopReadMapper.map(shop)).thenReturn(exceptResult);

        ShopReadDto actualResult = shopService.getShopByOrderId(order.getId());

        assertNotNull(actualResult);
        assertEquals(exceptResult.getId(), actualResult.getId());
        assertEquals(exceptResult.getName(), actualResult.getName());
        assertEquals(exceptResult.getAddress(), actualResult.getAddress());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getSpecialization(), actualResult.getSpecialization());
        assertEquals(exceptResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(exceptResult.isActive(), actualResult.isActive());
        assertEquals(exceptResult.getDescription(), actualResult.getDescription());
    }

    @Test
    void getShopByOrderId_throw_ShopNotFoundException() {


        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> shopService.getShopByOrderId(order.getId()));


    }

    @Test
    void getShopByOrderId_throw_OrderNotFoundException() {

        Order orderWithoutShop = new Order();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(orderWithoutShop));

        assertThrows(EntityNotFoundException.class, () -> shopService.getShopByOrderId(order.getId()));

    }

}