package org.shinmen.geektrust.dtos;

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

}
