package org.shinmen.geektrust.models;

import java.util.Objects;

public abstract class BaseEntity {
    String id;

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

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
