package org.shinmen.geektrust.services;

import java.util.List;

import org.shinmen.geektrust.dtos.BillResponse;
import org.shinmen.geektrust.exceptions.RiderException;

public interface IRiderService {
    void addRider(String riderId, double x, double y) throws RiderException;
    void addDriver(String riderId, double x, double y) throws RiderException;
    List<String> match(String riderId) throws RiderException;
    String startRide(String rideId, int driverOption, String riderId) throws RiderException;
    String stopRide(String rideId, double x, double y, double minutes) throws RiderException;
    BillResponse bill(String rideId) throws RiderException;
}