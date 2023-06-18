package com.ridesharing.geektrust.models;

import com.ridesharing.geektrust.utilities.Constants;

public abstract class User extends BaseEntity {
    private double x;
    private double y;

    protected User(String id, double x, double y) {
        super(id);
        this.x = x;
        this.y = y;
    }

    public double distanceTo(User other) {
        return Math.sqrt(Math.pow(this.x - other.x, Constants.POWER_TWO) + Math.pow(this.y - other.y, Constants.POWER_TWO));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
