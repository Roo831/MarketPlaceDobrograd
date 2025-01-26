package com.poptsov.marketplace.service;

import com.poptsov.marketplace.dto.UserReadDto;

import java.util.List;

public interface ReadService<T, ID> {

    T findById(ID id);

    List<T> findAll();

}
