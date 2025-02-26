package com.poptsov.marketplace.service;


import com.poptsov.marketplace.database.entity.User;
import com.poptsov.marketplace.dto.*;

import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;


public interface UserService extends CreateService<User, User>, ReadService<UserReadDto, Integer>, UpdateService<UserReadDto, UserEditDto> {

    //CRUD start

    @Override
    UserReadDto findById(Integer id);

    @Override
    List<UserReadDto> findAll();

    @Override
    User create(User user);

    @Override
    UserReadDto update(UserEditDto userEditDto);

    //CRUD end

    User findByEmail(String email);

    User findByUsername(String username);

    UserDetailsService userDetailsService();

    User findCurrentUser();

    UserReadDto updateUserAdminRights(Integer id, SwitchAdminDto switchAdminDto);

    UserReadDto getMyself();

    UserReadDto findOwnerByOrderId(Integer orderId);

    ShopReadDto findShopByUserId(Integer userId);

    List<OrderReadDto> findOrdersByUserId(Integer userId);

    boolean isUserBanned(String username);

}
