package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.UserEditDto;
import com.poptsov.marketplace.dto.UserReadDto;

public interface UpdateService<R, C> {

    R update(C entity);


}
