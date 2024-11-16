package com.poptsov.marketplace.dto;

import com.poptsov.marketplace.database.entity.Role;

import java.util.Date;

public class UserDto {

    private Integer id;

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String steamId;

    private Role role;

    private Boolean isAdmin;

    private Boolean isBanned;

    private Date createdAt;
}
