package org.shinmen.geektrust.repository;

import java.util.Map;

import org.shinmen.geektrust.models.BaseEntity;
import org.shinmen.geektrust.repository.interfaces.IGenericRepository;

public class GenericRepository<T extends BaseEntity> implements IGenericRepository<T> {
    protected final Map<String, T> map;

    public GenericRepository(Map<String, T> map) {
        this.map = map;
    }

    @Override
    public void save(T t) {
        map.put(t.getId(), t);
    }

    @Override
    public T getById(String id) {
        return map.get(id);
    }

    @Override
    public Map<String, T> getMap() {
        return map;
    }
}