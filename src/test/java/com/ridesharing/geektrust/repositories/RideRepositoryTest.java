package com.ridesharing.geektrust.repositories;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ridesharing.geektrust.models.Driver;
import com.ridesharing.geektrust.models.Ride;
import com.ridesharing.geektrust.models.Rider;
import com.ridesharing.geektrust.repository.RideRepository;
import com.ridesharing.geektrust.repository.interfaces.IRideRepository;

class RideRepositoryTest {

    private IRideRepository rideRepository;

    @BeforeEach
    void setup() {
        Map<String, Ride> rideMap = new HashMap<>();
        rideMap.put("RIDE-101", new Ride("RIDE-101", new Rider("R1", 1.0, 1.0), new Driver("D1", 3.0, 3.0), 1.0, 1.0));
        rideMap.put("RIDE-102", new Ride("RIDE-102", new Rider("R2", 2.0, 2.0), new Driver("D2", 4.0, 4.0), 2.0, 2.0));
        rideRepository = new RideRepository(rideMap);
    }

    @Test
    void getByIdGivenRideIdShouldReturnRide() {
        // Arrange
        Ride expectedRide = new Ride("RIDE-102", new Rider("R2", 2.0, 2.0), new Driver("D2", 4.0, 4.0), 2.0, 2.0);
        // Act
        Ride actualRide = rideRepository.getById("RIDE-102");
        // Assert
        Assertions.assertEquals(expectedRide, actualRide);
    }

    @Test
    void getByIdGivenRideIdShouldReturnNull() {
        // Arrange
        String rideId = "RIDE-103";
        // Act
        Ride ride = rideRepository.getById(rideId);
        // Assert
        Assertions.assertNull(ride);
    }

    @Test
    void saveShouldSaveRider() {
        // Arrange
        Ride ride = new Ride("RIDE-103", new Rider("R3", 3.0, 2.0), new Driver("D3", 2.0, 3.0), 3.0, 2.0);
        // Act
        rideRepository.save(ride);
        // Assert
        Assertions.assertEquals(ride, rideRepository.getById("RIDE-103"));
        Assertions.assertEquals(3, rideRepository.getMap().size());
    }
}