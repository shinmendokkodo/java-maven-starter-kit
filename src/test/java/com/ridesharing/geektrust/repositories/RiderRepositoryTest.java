package com.ridesharing.geektrust.repositories;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.ridesharing.geektrust.models.Rider;
import com.ridesharing.geektrust.repository.RiderRepository;
import com.ridesharing.geektrust.repository.interfaces.IRiderRepository;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RiderRepositoryTest {

    private IRiderRepository riderRepository;

    @BeforeEach
    void setup() {
        Map<String, Rider> riderMap = new HashMap<>();
        riderMap.put("R1", new Rider("R1", 1.0, 1.0));
        riderMap.put("R2", new Rider("R2", 2.0, 2.0));
        riderMap.put("R3", new Rider("R3", 3.0, 3.0));
        riderMap.put("R4", new Rider("R4", 4.0, 4.0));
        riderRepository = new RiderRepository(riderMap);
    }

    @Test
    void getByIdGivenRiderIdShouldReturnRider() {
        // Arrange
        Rider expectedRider = new Rider("R3", 3.0, 3.0);
        // Act
        Rider actualRider = riderRepository.getById("R3");
        // Assert
        Assertions.assertEquals(expectedRider, actualRider);
    }

    @Test
    void getByIdGivenRiderIdShouldReturnNull() {
        // Arrange
        String riderId = "R5";
        // Act
        Rider rider = riderRepository.getById(riderId);
        // Assert
        Assertions.assertNull(rider);
    }

    @Test
    void saveShouldSaveRider() {
        // Arrange
        Rider rider = new Rider("R5", 5.0, 5.0);
        // Act
        riderRepository.save(rider);
        // Assert
        Assertions.assertEquals(rider, riderRepository.getById("R5"));
        Assertions.assertEquals(5, riderRepository.getMap().size());
    }
}