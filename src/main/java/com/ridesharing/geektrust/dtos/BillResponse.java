package com.ridesharing.geektrust.dtos;

import java.util.Objects;

public class BillResponse {
    private String rideId;
    private String driverId;
    private double totalFare;

    public BillResponse(String rideId, String driverId, double totalFare) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.totalFare = totalFare;
    }

    @Override
    public String toString() {
        return rideId + " " + driverId + " " + totalFare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillResponse)) return false;
        BillResponse that = (BillResponse) o;
        return getRideId().equals(that.getRideId()) 
            && getDriverId().equals(that.getDriverId()) 
            && getTotalFare() == that.getTotalFare();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRideId());
    }

    public String getRideId() {
        return rideId;
    }

    public String getDriverId() {
        return driverId;
    }

    public double getTotalFare() {
        return totalFare;
    }
    
}
