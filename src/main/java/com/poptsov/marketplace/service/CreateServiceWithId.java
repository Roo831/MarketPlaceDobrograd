package com.poptsov.marketplace.service;


public interface CreateServiceWithId<R, C, ID> {
    R create(ID id, C entity);

}
