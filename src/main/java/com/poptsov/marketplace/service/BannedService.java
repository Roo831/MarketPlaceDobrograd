package com.poptsov.marketplace.service;


import com.poptsov.marketplace.dto.BanCreateDto;
import com.poptsov.marketplace.dto.BanReadDto;
import com.poptsov.marketplace.dto.UserReadDto;

import java.util.List;

public interface BannedService extends CreateServiceWithId<BanReadDto, BanCreateDto, Integer>, DeleteService<UserReadDto, Integer>, ReadService<BanReadDto, Integer> {

    //CRUD start

    @Override
    BanReadDto create(Integer userId, BanCreateDto banCreateDto);

    @Override
    UserReadDto delete(Integer id);

    @Override
    List<BanReadDto> findAll();

    @Override
    BanReadDto findById(Integer id);

    // CRUD end

}
