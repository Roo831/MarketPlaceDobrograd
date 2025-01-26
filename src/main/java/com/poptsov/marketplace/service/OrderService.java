package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.OrderCreateDto;
import com.poptsov.marketplace.dto.OrderEditStatusDto;
import com.poptsov.marketplace.dto.OrderReadDto;


import java.util.List;


public interface OrderService extends CreateServiceWithId<OrderReadDto, OrderCreateDto, Integer>,
        ReadService<OrderReadDto, Integer>, DeleteService<OrderReadDto, Integer> {

    // CRUD start

    @Override
    OrderReadDto findById(Integer orderId);

    @Override
    List<OrderReadDto> findAll();

    @Override
    OrderReadDto create(Integer shopId, OrderCreateDto orderCreateDto);


    @Override
    OrderReadDto delete(Integer id);

    // CRUD end

    OrderReadDto updateStatus(Integer id, OrderEditStatusDto orderEditStatusDto);

    List<OrderReadDto> findOrdersOfMyShop();

    List<OrderReadDto> findMyOrders();


}
