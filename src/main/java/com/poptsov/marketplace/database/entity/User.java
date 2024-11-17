package com.poptsov.marketplace.database.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String steamId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isAdmin;

    private Boolean isBanned;

    private Date createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Shop shop;
}
