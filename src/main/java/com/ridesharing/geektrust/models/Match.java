package com.ridesharing.geektrust.models;

import java.util.List;

public class Match extends BaseEntity {
    private List<String> driverIds;

    public Match(String riderId, List<String> driverIds) {
        super(riderId);
        this.driverIds = driverIds;
    }

    public List<String> getDriverIds() {
        return driverIds;
    }
}
