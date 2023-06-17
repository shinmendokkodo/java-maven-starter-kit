package org.shinmen.geektrust.repositories;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shinmen.geektrust.models.Driver;
import org.shinmen.geektrust.repository.DriverRepository;
import org.shinmen.geektrust.repository.interfaces.IDriverRepository;

class DriverRepositoryTest {

    private IDriverRepository driverRepository;

    @BeforeEach
    void setup() {
        Map<String, Driver> driverMap = new HashMap<>();
        driverMap.put("D1", new Driver("D1", 1.0, 1.0));
        driverMap.put("D2", new Driver("D2", 2.0, 2.0));
        driverMap.put("D3", new Driver("D3", 3.0, 3.0));
        driverMap.put("D4", new Driver("D4", 4.0, 4.0));
        driverRepository = new DriverRepository(driverMap);
    }

    @Test
    void getById_GivenDriverId_ShouldReturnDriver() {
        // Arrange
        Driver expectedDriver = new Driver("D3", 3.0, 3.0);
        // Act
        Driver actualDriver = driverRepository.getById("D3");
        // Assert
        Assertions.assertEquals(expectedDriver, actualDriver);
    }

    @Test
    void getById_GivenDriverId_ShouldReturnNull() {
        // Arrange
        String driverId = "D5";
        // Act
        Driver driver = driverRepository.getById(driverId);
        // Assert
        Assertions.assertNull(driver);
    }

    @Test
    void save_ShouldSaveDriver() {
        // Arrange
        Driver driver = new Driver("D5", 5.0, 5.0);
        // Act
        driverRepository.save(driver);
        // Assert
        Assertions.assertEquals(driver, driverRepository.getById("D5"));
        Assertions.assertEquals(5, driverRepository.getMap().size());
    }
}