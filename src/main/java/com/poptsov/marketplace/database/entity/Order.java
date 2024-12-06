package com.poptsov.marketplace.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String name;

    private String description;

    private Integer price;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public Order (Integer id){
        this.id = id;
    }

}
