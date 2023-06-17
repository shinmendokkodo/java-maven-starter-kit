package org.shinmen.geektrust.models;

public class Ride extends BaseEntity {
    Rider rider;
    Driver driver;
    double startX;
    double startY;
    double endX;
    double endY;
    double timeTaken;

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
}
