package org.shinmen.geektrust.repository;

import java.util.Map;

import org.shinmen.geektrust.models.Ride;
import org.shinmen.geektrust.repository.interfaces.IRideRepository;

public class RideRepository extends GenericRepository<Ride> implements IRideRepository {
    public RideRepository(Map<String, Ride> rideMap) {
        super(rideMap);
    }
}
