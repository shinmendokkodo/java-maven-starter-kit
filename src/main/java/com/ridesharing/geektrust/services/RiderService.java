package com.ridesharing.geektrust.services;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.ridesharing.geektrust.dtos.BillResponse;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.models.Driver;
import com.ridesharing.geektrust.models.Match;
import com.ridesharing.geektrust.models.Ride;
import com.ridesharing.geektrust.models.Rider;
import com.ridesharing.geektrust.repository.interfaces.IDriverRepository;
import com.ridesharing.geektrust.repository.interfaces.IMatchRepository;
import com.ridesharing.geektrust.repository.interfaces.IRideRepository;
import com.ridesharing.geektrust.repository.interfaces.IRiderRepository;
import com.ridesharing.geektrust.utilities.Constants;

public class RiderService implements IRiderService {
    private final IRideRepository rideRepository;
    private final IDriverRepository driverRepository;
    private final IMatchRepository matchRepository;
    private final IRiderRepository riderRepository;

    public RiderService(IRideRepository rideRepository, IDriverRepository driverRepository,
            IMatchRepository matchRepository, IRiderRepository riderRepository) {
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
        this.matchRepository = matchRepository;
        this.riderRepository = riderRepository;
    }

    @Override
    public void addRider(String riderId, double x, double y) throws RiderException {
        Rider rider = riderRepository.getById(riderId);
        if (rider != null)
            throw new RiderException(Constants.ENTITY_EXISTS_MESSAGE);

        rider = new Rider(riderId, x, y);
        riderRepository.save(rider);
    }

    @Override
    public void addDriver(String driverId, double x, double y) throws RiderException {
        Driver driver = driverRepository.getById(driverId);
        if (driver != null)
            throw new RiderException(Constants.ENTITY_EXISTS_MESSAGE);

        driver = new Driver(driverId, x, y);
        driverRepository.save(driver);
    }

    @Override
    public List<String> match(String riderId) throws RiderException {
        Rider rider = riderRepository.getById(riderId);
        if (rider == null)
            throw new RiderException(Constants.ENTITY_NOT_FOUND_MESSAGE);

        PriorityQueue<Driver> pq = new PriorityQueue<>((a, b) -> {
            double distanceA = a.distanceTo(rider);
            double distanceB = b.distanceTo(rider);
            if (distanceA != distanceB) {
                return Double.compare(distanceA, distanceB);
            }
            return a.getId().compareTo(b.getId());
        });

        for (Driver driver : driverRepository.getAvailableDrivers()) {
            double distance = driver.distanceTo(rider);
            if (driver.isAvailable() && distance <= Constants.DRIVERS_DISTANCE) {
                pq.offer(driver);
            }
        }

        List<String> matchedDrivers = new ArrayList<>();

        if (pq.isEmpty()) {
            matchRepository.save(new Match(riderId, matchedDrivers));
            throw new RiderException(Constants.NO_DRIVERS_AVAILABLE_MESSAGE);
        }

        while (!pq.isEmpty() && matchedDrivers.size() < Constants.DRIVERS_SIZE) {
            matchedDrivers.add(pq.poll().getId());
        }

        matchRepository.save(new Match(riderId, matchedDrivers));
        return matchedDrivers;
    }

    @Override
    public String startRide(String rideId, int driverOption, String riderId) throws RiderException {

        Match match = matchRepository.getById(riderId);
        if (match == null)
            throw new RiderException(Constants.ENTITY_NOT_FOUND_MESSAGE);

        Ride ride = rideRepository.getById(rideId);
        if (ride != null)
            throw new RiderException(Constants.INVALID_RIDE_MESSAGE);

        if (match.getDriverIds().size() < driverOption)
            throw new RiderException(Constants.INVALID_RIDE_MESSAGE);

        String driverId = match.getDriverIds().get(driverOption - Constants.ONE);
        Driver driver = driverRepository.getById(driverId);
        if (driver == null || !driver.isAvailable())
            throw new RiderException(Constants.INVALID_RIDE_MESSAGE);

        Rider rider = riderRepository.getById(riderId);
        if (rider == null)
            throw new RiderException(Constants.ENTITY_NOT_FOUND_MESSAGE);

        driver.setAvailable(false);
        Ride newRide = new Ride(rideId, rider, driver, rider.getX(), rider.getY());

        driverRepository.save(driver);
        rideRepository.save(newRide);
        return newRide.getId();
    }

    @Override
    public String stopRide(String rideId, double x, double y, double minutes) throws RiderException {

        Ride ride = rideRepository.getById(rideId);
        if (ride == null)
            throw new RiderException(Constants.INVALID_RIDE_MESSAGE);

        if (ride.isRideOver())
            throw new RiderException(Constants.INVALID_RIDE_MESSAGE);

        ride.stopRide(x, y, minutes);

        Driver driver = ride.getDriver();
        driver.setAvailable(true);
        ride.setDriver(driver);

        driverRepository.save(driver);
        rideRepository.save(ride);

        return ride.getId();
    }

    @Override
    public BillResponse bill(String rideId) throws RiderException {
        Ride ride = rideRepository.getById(rideId);
        if (ride == null)
            throw new RiderException(Constants.INVALID_RIDE_MESSAGE);

        if (!ride.isRideOver())
            throw new RiderException(Constants.RIDE_NOT_COMPLETED_MESSAGE);

        double distanceTraveled = Math
                .sqrt(Math.pow(ride.getEndX() - ride.getStartX(), Constants.POWER_TWO)
                        + Math.pow(ride.getEndY() - ride.getStartY(), Constants.POWER_TWO));
        distanceTraveled = Math.round(distanceTraveled * Constants.MULTIPLIER) / Constants.MULTIPLIER;

        double baseFare = Constants.BASE_FARE;
        double distanceFare = Constants.distanceFare(distanceTraveled);
        double timeFare = Constants.timeFare(ride.getTimeTaken());
        double serviceTax = Constants.taxedFare(baseFare + distanceFare + timeFare);

        double totalAmount = baseFare + distanceFare + timeFare + serviceTax;
        totalAmount = Math.round(totalAmount * Constants.MULTIPLIER) / Constants.MULTIPLIER; // rounding to two decimal
                                                                                             // places

        return new BillResponse(ride.getId(), ride.getDriver().getId(), totalAmount);
    }
}