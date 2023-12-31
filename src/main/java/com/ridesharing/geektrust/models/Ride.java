package com.ridesharing.geektrust.models;

public class Ride extends BaseEntity {
    private Rider rider;
    private Driver driver;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double timeTaken;

    public Ride(String id, Rider rider, Driver driver, double startX, double startY) {
        super(id);
        this.rider = rider;
        this.driver = driver;
        this.startX = startX;
        this.startY = startY;
    }

    public boolean isRideOver() {
        return endX != 0 && endY != 0;
    }

    public void stopRide(double endX, double endY, double timeTaken) {
        this.endX = endX;
        this.endY = endY;
        this.timeTaken = timeTaken;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
