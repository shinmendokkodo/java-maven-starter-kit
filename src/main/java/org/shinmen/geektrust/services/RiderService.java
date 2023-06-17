package org.shinmen.geektrust.services;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.shinmen.geektrust.dtos.BillResponse;
import org.shinmen.geektrust.exceptions.RiderException;
import org.shinmen.geektrust.models.Driver;
import org.shinmen.geektrust.models.Match;
import org.shinmen.geektrust.models.Ride;
import org.shinmen.geektrust.models.Rider;
import org.shinmen.geektrust.repository.interfaces.IDriverRepository;
import org.shinmen.geektrust.repository.interfaces.IMatchRepository;
import org.shinmen.geektrust.repository.interfaces.IRideRepository;
import org.shinmen.geektrust.repository.interfaces.IRiderRepository;
import org.shinmen.geektrust.utilities.Constants;

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

        for (Driver driver : driverRepository.getAll()) {
            double distance = driver.distanceTo(rider);
            if (driver.isAvailable() && distance <= 5.0) {
                pq.offer(driver);
            }
        }

        if (pq.isEmpty())
            throw new RiderException(Constants.NO_DRIVERS_AVAILABLE_MESSAGE);

        List<String> matchedDrivers = new ArrayList<>();
        while (!pq.isEmpty() && matchedDrivers.size() < 5) {
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

        String driverId = match.getDriverIds().get(driverOption - 1);
        Driver driver = driverRepository.getById(driverId);
        if (driver == null)
            throw new RiderException(Constants.ENTITY_NOT_FOUND_MESSAGE);
        if (!driver.isAvailable())
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

        rideRepository.save(ride);
        driverRepository.save(driver);
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
                .sqrt(Math.pow(ride.getEndX() - ride.getStartX(), 2) + Math.pow(ride.getEndY() - ride.getStartY(), 2));
        distanceTraveled = Math.round(distanceTraveled * 100.0) / 100.0;

        double baseFare = Constants.BASE_FARE;
        double distanceFare = Constants.distanceFare(distanceTraveled);
        double timeFare = Constants.timeFare(ride.getTimeTaken());
        double serviceTax = Constants.taxedFare(baseFare + distanceFare + timeFare);

        double totalAmount = baseFare + distanceFare + timeFare + serviceTax;
        totalAmount = Math.round(totalAmount * 100.0) / 100.0; // rounding to two decimal places

        return new BillResponse(ride.getId(), ride.getDriver().getId(), totalAmount);
    }
}