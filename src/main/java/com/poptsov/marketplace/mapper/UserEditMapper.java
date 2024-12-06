package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.UserEditDto;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper implements Mapper<UserEditDto, User>{



    @Override
    public User map(UserEditDto object) { // Не используется. Создано чтобы не нарушать контракт с интерфейсом
        return null;
    }


    public User map(Integer id, UserEditDto userEditDto) {
        User user = User.builder().id(id).build();
        user.setFirstname(userEditDto.getFirstname());
        user.setLastname(userEditDto.getLastname());
        return user;
    }
}
