package com.poptsov.marketplace.service;

import com.poptsov.marketplace.database.entity.Order;
import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import com.poptsov.marketplace.dto.OrderReadDto;
import com.poptsov.marketplace.exceptions.AuthorizationException;
import com.poptsov.marketplace.exceptions.EntityNotFoundException;
import com.poptsov.marketplace.mapper.OrderCreateMapper;
import com.poptsov.marketplace.mapper.OrderReadMapper;
import com.poptsov.marketplace.util.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private OrderReadMapper orderReadMapper;

    @Mock
    private OrderCreateMapper orderCreateMapper;

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
    void findById_get() {

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        OrderReadDto exceptResult = OrderReadDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .userId(user.getId())
                .shopId(shop.getId())
                .build();
        when(orderReadMapper.map(any())).thenReturn(exceptResult);

        OrderReadDto actualResult = orderService.findById(order.getId());

        assertNotNull(actualResult);
        assertEquals(order.getId(), actualResult.getOrderId());
        assertEquals(order.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(order.getStatus(), actualResult.getStatus());
        assertEquals(order.getName(), actualResult.getName());
        assertEquals(order.getDescription(), actualResult.getDescription());
        assertEquals(order.getPrice(), actualResult.getPrice());

    }

    @Test
    void findById_throw() {

        when(orderRepository.findById(order.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> orderService.findById(order.getId()));

    }

    @Test
    void create_get() {

        OrderCreateDto orderCreateDto = OrderCreateDto.builder()
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .build();

        OrderReadDto expectResult = OrderReadDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .userId(user.getId())
                .shopId(shop.getId())
                .build();


        when(userService.findCurrentUser()).thenReturn(user);
        when(shopRepository.findById(shop.getId())).thenReturn(Optional.of(shop));
        when(orderCreateMapper.map(orderCreateDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderReadMapper.map(order)).thenReturn(expectResult);


        OrderReadDto actualResult = orderService.create(shop.getId(), orderCreateDto);

        assertNotNull(actualResult);
        assertEquals(expectResult.getOrderId(), actualResult.getOrderId());
        assertEquals(expectResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectResult.getStatus(), actualResult.getStatus());
        assertEquals(expectResult.getName(), actualResult.getName());
        assertEquals(expectResult.getDescription(), actualResult.getDescription());
        assertEquals(expectResult.getPrice(), actualResult.getPrice());

    }

    @Test
    void create_throws() {

        OrderCreateDto orderCreateDto = OrderCreateDto.builder()
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .build();

        when(shopRepository.findById(shop.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> orderService.create(shop.getId(),orderCreateDto));
    }

    @Test
    void update_get() {

        OrderEditStatusDto orderEditStatusDto = OrderEditStatusDto.builder()
                .status(order.getStatus())
                .build();

        OrderReadDto expectResult = OrderReadDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .userId(user.getId())
                .shopId(shop.getId())
                .build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(userService.findCurrentUser()).thenReturn(user);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderReadMapper.map(order)).thenReturn(expectResult);

        OrderReadDto actualResult = orderService.update(order.getId(), orderEditStatusDto);

        assertNotNull(actualResult);
        assertEquals(expectResult.getOrderId(), actualResult.getOrderId());
        assertEquals(expectResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectResult.getStatus(), actualResult.getStatus());
        assertEquals(expectResult.getName(), actualResult.getName());
        assertEquals(expectResult.getDescription(), actualResult.getDescription());
        assertEquals(expectResult.getPrice(), actualResult.getPrice());
    }

    @Test
    void update_throw_NotFoundException() {

        OrderEditStatusDto orderEditStatusDto = OrderEditStatusDto.builder()
                .status(order.getStatus())
                .build();

        when(orderRepository.findById(order.getId())).thenThrow(EntityNotFoundException.class);


        assertThrows(EntityNotFoundException.class, () -> orderService.update(order.getId(), orderEditStatusDto));
    }

    @Test
    void update_throw_AuthorizationException() {

        OrderEditStatusDto orderEditStatusDto = OrderEditStatusDto.builder()
                .status(order.getStatus())
                .build();

      User notAreOwnerOfShop = User.builder()
              .id(999)
              .build();

        when(orderRepository.findById(any())).thenReturn(Optional.of(order));
        when(userService.findCurrentUser()).thenReturn(notAreOwnerOfShop);

        assertThrows(AuthorizationException.class, () -> orderService.update(order.getId(), orderEditStatusDto));
    }

    @Test
    void delete_get() {
        boolean exceptedResult = true;

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(userService.findCurrentUser()).thenReturn(user);

        boolean actualResult = orderService.delete(order.getId());

        assertEquals(exceptedResult, actualResult);
    }

    @Test
    void delete_throw_NotFoundException() {
        when(orderRepository.findById(order.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> orderService.delete(order.getId()));
    }

    @Test
    void delete_throw_AuthorizationException() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        User notAreOwnerOfShop = User.builder()
                .id(999)
                .build();
        when(userService.findCurrentUser()).thenReturn(notAreOwnerOfShop);

        assertThrows(AuthorizationException.class, () -> orderService.delete(order.getId()));
    }

    @Test
    void findOrdersOfMyShop_get() {
        OrderReadDto expectedResult = OrderReadDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .shopId(order.getShop().getId())
                .build();

        when(userService.findCurrentUser()).thenReturn(user);
        when(orderReadMapper.map(order)).thenReturn(expectedResult);

        OrderReadDto actualResult = orderService.findOrdersOfMyShop().get(0);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        assertEquals(expectedResult.getName(), actualResult.getName());
        assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        assertEquals(expectedResult.getPrice(), actualResult.getPrice());

    }

    @Test
    void findOrdersOfMyShop_throw() {

        user.setShop(null);
        when(userService.findCurrentUser()).thenReturn(user);
        assertThrows(EntityNotFoundException.class, () -> orderService.findOrdersOfMyShop());

    }

    @Test
    void findMyOrders_get() {

        OrderReadDto expectedResult = OrderReadDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .status(order.getStatus())
                .name(order.getName())
                .description(order.getDescription())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .shopId(order.getShop().getId())
                .build();
        when(userService.findCurrentUser()).thenReturn(user);
        when(orderReadMapper.map(order)).thenReturn(expectedResult);

        OrderReadDto actualResult = orderService.findMyOrders().get(0);

        assertNotNull(actualResult);
        assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
        assertEquals(expectedResult.getStatus(), actualResult.getStatus());
        assertEquals(expectedResult.getName(), actualResult.getName());
        assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        assertEquals(expectedResult.getPrice(), actualResult.getPrice());

    }

}