package org.shinmen.geektrust.models;

public class Driver extends User {
    private boolean isAvailable;

    public Driver(String id, double x, double y) {
        super(id, x, y);
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}