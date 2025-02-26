package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.ShopCreateDto;
import com.poptsov.marketplace.dto.ShopEditDto;
import com.poptsov.marketplace.dto.ShopEditStatusDto;
import com.poptsov.marketplace.dto.ShopReadDto;


import java.util.List;


public interface ShopService extends CreateService<ShopReadDto, ShopCreateDto>, ReadService<ShopReadDto, Integer>, UpdateService<ShopReadDto, ShopEditDto> {

    // CRUD start

    @Override
    ShopReadDto create(ShopCreateDto shopCreateDto);

    @Override
    ShopReadDto update(ShopEditDto shopEditDto);

    @Override
    ShopReadDto findById(Integer id);

    @Override
    List<ShopReadDto> findAll();

    boolean deleteMyShop();

    // CRUD end

    List<ShopReadDto> findActiveShops();

    ShopReadDto switchActiveStatusOfMyShop(ShopEditStatusDto shopEditStatusDto);

    ShopReadDto findMyShop();

    ShopReadDto getShopByOrderId(Integer orderId);
}
