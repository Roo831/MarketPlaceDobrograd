package com.poptsov.marketplace.database.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
@Column(name = "email")
    private String email;
@Column(name = "password")
    private String password;
@Column(name= "firstname")
    private String firstname;
@Column(name = "lastname")
    private String lastname;
@Column(name = "steam_id")
    private String steamId;
@Column(name = "role")
    private Role role;
}
