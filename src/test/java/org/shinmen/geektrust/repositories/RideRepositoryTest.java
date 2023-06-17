package org.shinmen.geektrust.repositories;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shinmen.geektrust.models.Driver;
import org.shinmen.geektrust.models.Ride;
import org.shinmen.geektrust.models.Rider;
import org.shinmen.geektrust.repository.RideRepository;
import org.shinmen.geektrust.repository.interfaces.IRideRepository;

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
    void getById_GivenRideId_ShouldReturnRide() {
        // Arrange
        Ride expectedRide = new Ride("RIDE-102", new Rider("R2", 2.0, 2.0), new Driver("D2", 4.0, 4.0), 2.0, 2.0);
        // Act
        Ride actualRide = rideRepository.getById("RIDE-102");
        // Assert
        Assertions.assertEquals(expectedRide, actualRide);
    }

    @Test
    void getById_GivenRideId_ShouldReturnNull() {
        // Arrange
        String rideId = "RIDE-103";
        // Act
        Ride ride = rideRepository.getById(rideId);
        // Assert
        Assertions.assertNull(ride);
    }

    @Test
    void save_ShouldSaveRider() {
        // Arrange
        Ride ride = new Ride("RIDE-103", new Rider("R3", 3.0, 2.0), new Driver("D3", 2.0, 3.0), 3.0, 2.0);
        // Act
        rideRepository.save(ride);
        // Assert
        Assertions.assertEquals(ride, rideRepository.getById("RIDE-103"));
        Assertions.assertEquals(3, rideRepository.getMap().size());
    }
}