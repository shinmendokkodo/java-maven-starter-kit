package com.ridesharing.geektrust.repository.interfaces;

import java.util.Map;

import com.ridesharing.geektrust.models.BaseEntity;

public interface IGenericRepository<T extends BaseEntity> {
    void save(T t);
    T getById(String id);
    Map<String, T> getMap();
}
