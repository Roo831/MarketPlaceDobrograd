package com.poptsov.marketplace.mapper;

import com.poptsov.marketplace.database.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper implements Mapper<UserRoleDto, User> {


    private void copy(User user, UserRoleDto userRoleDto) {

        user.setRole(userRoleDto.getRole());

    }

    @Override
    public User map(UserRoleDto object) {
        User user = new User();
        copy(user, object);
        return user;
    }



    public User map(Integer id, UserRoleDto userRoleDto) {
        User user = User.builder().id(id).build();
        copy(user, userRoleDto);
        return user;
    }
}
