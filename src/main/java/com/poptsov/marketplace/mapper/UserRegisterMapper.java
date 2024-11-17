package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.RegisterDto;
import org.springframework.stereotype.Component;


@Component
public class UserRegisterMapper implements Mapper<RegisterDto, User> {

    @Override
    public User map(RegisterDto object) {
        User user = new User();
        copy(user, object);
        return user;
    }

    private void copy(User user, RegisterDto registerDto) {

        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());

    }
}
