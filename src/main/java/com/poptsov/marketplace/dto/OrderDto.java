package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Shop;
import com.poptsov.marketplace.database.entity.Status;
import com.poptsov.marketplace.database.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {

    private Integer orderId;

    private Date createdAt;

    private Status status;

    private String name;

    private String description;

    private Integer price;

    private Integer userId;

    private Integer shopId;
}
