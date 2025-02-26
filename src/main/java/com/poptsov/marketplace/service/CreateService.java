package com.poptsov.marketplace.service;

public interface CreateService<R, T> {
    R create(T entity);
}
