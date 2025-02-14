package com.poptsov.marketplace.database.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(steamId, user.steamId) && role == user.role && Objects.equals(isAdmin, user.isAdmin) && Objects.equals(isBanned, user.isBanned) && Objects.equals(isVerified, user.isVerified) && Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, firstname, lastname, steamId, role, isAdmin, isBanned, isVerified, createdAt);
    }
}