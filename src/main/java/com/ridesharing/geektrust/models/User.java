package com.ridesharing.geektrust.models;

public abstract class User extends BaseEntity {
    private double x;
    private double y;

    protected User(String id, double x, double y) {
        super(id);
        this.x = x;
        this.y = y;
    }

    public double distanceTo(User other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
