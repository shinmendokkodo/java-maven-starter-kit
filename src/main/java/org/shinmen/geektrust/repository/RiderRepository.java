package org.shinmen.geektrust.repository;

import java.util.Map;

import org.shinmen.geektrust.models.Rider;
import org.shinmen.geektrust.repository.interfaces.IRiderRepository;

public class RiderRepository extends GenericRepository<Rider> implements IRiderRepository {
    public RiderRepository(Map<String, Rider> riderMap) {
        super(riderMap);
    }
}