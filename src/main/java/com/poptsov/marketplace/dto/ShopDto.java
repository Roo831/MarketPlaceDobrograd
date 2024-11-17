package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Specialization;
import com.poptsov.marketplace.database.entity.User;

import java.util.Date;

public class ShopDto {

    private Integer id;

    private String name;

    private String address;

    private Integer rating;

    private Specialization specialization;

    private Date createdAt;

    private String description;

    private Integer userId;

}
