package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.UserEditDto;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper implements Mapper<UserEditDto, User>{


    private void copy(User user, UserEditDto userEditDto) {

        user.setFirstname(userEditDto.getFirstname());
        user.setLastname(userEditDto.getLastname());

    }

    @Override
    public User map(UserEditDto object) {
        User user = new User();
        copy(user, object);
        return user;
    }

    @Override
    public User map(UserEditDto fromObject, User toObject) {

        copy(toObject, fromObject);
        return toObject;
    }
}
