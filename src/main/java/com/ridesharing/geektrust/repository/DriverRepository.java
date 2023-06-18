package com.ridesharing.geektrust.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ridesharing.geektrust.models.Driver;
import com.ridesharing.geektrust.repository.interfaces.IDriverRepository;

public class DriverRepository extends GenericRepository<Driver> implements IDriverRepository {
    public DriverRepository(Map<String, Driver> driverMap) {
        super(driverMap);
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        return new ArrayList<>(map.values().stream().filter(Driver::isAvailable).collect(Collectors.toList()));
    }
}
