package com.ridesharing.geektrust.repository;

import java.util.Map;

import com.ridesharing.geektrust.models.Ride;
import com.ridesharing.geektrust.repository.interfaces.IRideRepository;

public class RideRepository extends GenericRepository<Ride> implements IRideRepository {
    public RideRepository(Map<String, Ride> rideMap) {
        super(rideMap);
    }
}
