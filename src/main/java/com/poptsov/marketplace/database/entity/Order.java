package com.poptsov.marketplace.database.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;

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
    @JsonBackReference
    private User user;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public Order (Integer id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(createdAt, order.createdAt) && status == order.status && Objects.equals(name, order.name) && Objects.equals(description, order.description) && Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, status, name, description, price);
    }
}
