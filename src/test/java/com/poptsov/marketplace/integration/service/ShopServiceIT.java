package com.poptsov.marketplace.integration.service;


import com.poptsov.marketplace.MarketplaceApplication;
import com.poptsov.marketplace.annotations.IT;
import com.poptsov.marketplace.database.entity.*;
import com.poptsov.marketplace.database.repository.OrderRepository;
import com.poptsov.marketplace.database.repository.ShopRepository;
import com.poptsov.marketplace.dto.ShopReadDto;
import com.poptsov.marketplace.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ShopServiceIT {

    private static final Integer SHOP_ID = 2;
    private final ShopService shopService;



    public static User getTestUserSeller() {

        User seller = new User();
        seller.setId(1);
        seller.setUsername("Test Seller Username");
        seller.setEmail("test.seller@example.com");
        seller.setFirstname("Test Seller Firstname");
        seller.setSteamId("Test Seller Steam ID");
        seller.setLastname("Test Seller Lastname");
        seller.setRole(Role.user);
        seller.setPassword("Test Seller Password");
        seller.setShop(getTestShop());
        seller.setOrders(null); // У продавца нет заказов
        seller.setIsBanned(false);
        seller.setIsAdmin(false);
        return seller;
    }

    public static User getTestUserBuyer() {
        User buyer = new User();
        buyer.setId(1);
        buyer.setIsAdmin(false);
        buyer.setUsername("Test Buyer Username");
        buyer.setEmail("test.buyer@example.com");
        buyer.setFirstname("Test Buyer Firstname");
        buyer.setSteamId("Test Buyer Steam ID");
        buyer.setLastname("Test Buyer Lastname");
        buyer.setRole(Role.user);
        buyer.setPassword("Test Buyer Password");
        buyer.setShop(null); // У покупателя нет магазина
        buyer.setOrders(List.of(getTestOrder()));
        buyer.setIsBanned(false);
        return buyer;
    }

    public static Order getTestOrder() {
        Order order = new Order();
        order.setId(1);
        order.setCreatedAt(new Date());
        order.setStatus(Status.pending);
        order.setName("Test Order Name");
        order.setDescription("Test Order Description");
        order.setPrice(3000);
        order.setUser(getTestUserBuyer());
        order.setShop(getTestShop());
        return order;
    }

    public static Shop getTestShop() {
        Shop shop = new Shop();
        shop.setId(1);
        shop.setName("Test Shop Name");
        shop.setAddress("Test Shop Address");
        shop.setRating(1);
        shop.setSpecialization(Specialization.chief);
        shop.setCreatedAt(new Date());
        shop.setActive(false);
        shop.setDescription("Test Shop Description");
        shop.setUser(getTestUserSeller());
        shop.setOrders(List.of(getTestOrder()));
        return shop;
    }


    @Test
    void findById() {
        // Вызываем метод findById
        ShopReadDto actualResult = shopService.findById(SHOP_ID);

        // Проверяем, что результат не равен null
        assertNotNull(actualResult);

        // Создаем ожидаемый результат
        ShopReadDto expectedResult = ShopReadDto.builder()
                .id(SHOP_ID)
                .build();

        // Проверяем, что фактический результат соответствует ожидаемому
        assertEquals(expectedResult, actualResult);
    }
}




