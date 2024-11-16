package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.Status;
import com.poptsov.marketplace.database.entity.User;

import java.util.Date;

public class OrderCreateDto {

    private Date createdAt;

    private Status status;

    private String name;

    private String description;

    private Integer price;

    private User user;

    private Shop shop;


}
