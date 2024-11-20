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
    public User map(UserEditDto object) { // Не используется
        User user = new User();
        copy(user, object);
        return user;
    }



    public User map(Integer id, UserEditDto userEditDto) {
        User user = User.builder().id(id).build();
        copy(user, userEditDto);
        return user;
    }
}
