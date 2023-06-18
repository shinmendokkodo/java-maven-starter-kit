package com.ridesharing.geektrust.repository.interfaces;

import java.util.List;

import com.ridesharing.geektrust.models.Driver;

public interface IDriverRepository extends IGenericRepository<Driver> {
    List<Driver> getAvailableDrivers();
}
