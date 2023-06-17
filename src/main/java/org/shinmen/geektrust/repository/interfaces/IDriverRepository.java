package org.shinmen.geektrust.repository.interfaces;

import java.util.List;

import org.shinmen.geektrust.models.Driver;

public interface IDriverRepository extends IGenericRepository<Driver> {
    List<Driver> getAll();
}
