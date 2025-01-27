package com.poptsov.marketplace.database.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private Integer rating;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    private Date createdAt;

    private boolean active;

    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public Shop (Integer id){
        this.id = id;
    }

    public void addOrder(Order order){
        orders.add(order);
        order.setShop(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return active == shop.active && Objects.equals(id, shop.id) && Objects.equals(name, shop.name) && Objects.equals(address, shop.address) && Objects.equals(rating, shop.rating) && specialization == shop.specialization && Objects.equals(createdAt, shop.createdAt) && Objects.equals(description, shop.description) && Objects.equals(user, shop.user) && Objects.equals(orders, shop.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, rating, specialization, createdAt, active, description, user, orders);
    }
}
