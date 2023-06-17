package org.shinmen.geektrust.repository.interfaces;

import java.util.Map;

import org.shinmen.geektrust.models.BaseEntity;

public interface IGenericRepository<T extends BaseEntity> {
    void save(T t);
    T getById(String id);
    Map<String, T> getMap();
}
