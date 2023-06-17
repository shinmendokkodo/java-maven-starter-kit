package org.shinmen.geektrust.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shinmen.geektrust.models.Driver;
import org.shinmen.geektrust.repository.interfaces.IDriverRepository;

public class DriverRepository extends GenericRepository<Driver> implements IDriverRepository {
    public DriverRepository(Map<String, Driver> driverMap) {
        super(driverMap);
    }

    @Override
    public List<Driver> getAll() {
        return new ArrayList<>(map.values());
    }
}
