package com.poptsov.marketplace.database.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {

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

    private Boolean isVerified;

    private Date createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Shop shop;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        System.out.println("Authorities: " + authorities);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User (Integer id){
        this.id = id;
    }

    public void addOrder(Order order){
        orders.add(order);
        order.setUser(this);
    }

}