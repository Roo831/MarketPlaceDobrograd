package com.poptsov.marketplace.util;

import com.poptsov.marketplace.database.entity.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Date;

public class MockEntityUtil {

    @Value("${passwords.junit}")
    private static final String encodedPasswordUnit = "";
    @Value("${passwords.it}")
    private static final String encodedPasswordIT = "";

    public static Banned getTestBanned() {
        Banned banned = new Banned();

        banned.setId(1);
        banned.setBanDate(new Date());
        banned.setDescriptionOfBan("Test Banned");
        banned.setUser(new User(1));
        return banned;
    }

    public static User getTestUserBanned() {
        User user = new User();

        user.setId(1);
        user.setUsername("Test User Username");
        user.setEmail("test.user@example.com");
        user.setFirstname("Test User Firstname");
        user.setSteamId("Test User Steam ID");
        user.setLastname("Test User Lastname");
        user.setRole(Role.user);
        user.setPassword(encodedPasswordUnit);
        user.setShop(new Shop(1));
        user.setOrders(Collections.singletonList(new Order(1)));
        user.setIsBanned(true);
        user.setIsAdmin(false);
        user.setIsVerified(true);
        return user;
    }

    public static User getTestUser() {
        User user = new User();

        user.setId(1);
        user.setUsername("Test User Username");
        user.setEmail("test.user@example.com");
        user.setFirstname("Test User Firstname");
        user.setSteamId("Test User Steam ID");
        user.setLastname("Test User Lastname");
        user.setRole(Role.user);
        user.setPassword(encodedPasswordUnit);
        user.setShop(new Shop(1));
        user.setOrders(Collections.singletonList(new Order(1)));
        user.setIsBanned(false);
        user.setIsAdmin(false);
        user.setIsVerified(true);
        return user;
    }

    public static Order getTestOrder() {
        Order order = new Order();

        order.setId(1);
        order.setCreatedAt(new Date());
        order.setStatus(Status.pending);
        order.setName("Test Order Name");
        order.setDescription("Test Order Description");
        order.setPrice(3000);
        order.setUser(new User(1));
        order.setShop(new Shop(1));
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
        shop.setOrders(Collections.singletonList(new Order(1)));
        shop.setUser(new User(1));
        return shop;
    }

    public static User getTestUserIT() {
        User user = new User();

        user.setId(1);
        user.setUsername("Roo831");
        user.setEmail("helpmeplsmen@gmail.com");
        user.setFirstname("Admin");
        user.setSteamId("STEAM_1:0:203160294");
        user.setLastname("Adminovich");
        user.setRole(Role.admin);
        user.setPassword(encodedPasswordIT);
        user.setIsBanned(false);
        user.setIsAdmin(true);
        user.setIsVerified(true);
        return user;
    }
}
