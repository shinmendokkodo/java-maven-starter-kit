package com.ridesharing.geektrust.models;

public abstract class BaseEntity {
    private String id;

    public String getId() {
        return id;
    }

    protected BaseEntity(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BaseEntity))
            return false;
        BaseEntity baseEntity = (BaseEntity) o;
        return getId().equals(baseEntity.getId());
    }
}
