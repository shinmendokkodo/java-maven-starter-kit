package com.ridesharing.geektrust.repository;

import java.util.Map;

import com.ridesharing.geektrust.models.Rider;
import com.ridesharing.geektrust.repository.interfaces.IRiderRepository;

public class RiderRepository extends GenericRepository<Rider> implements IRiderRepository {
    public RiderRepository(Map<String, Rider> riderMap) {
        super(riderMap);
    }
}