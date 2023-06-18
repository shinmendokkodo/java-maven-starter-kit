package com.ridesharing.geektrust.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class RiderServiceTest {

    @Mock
    private IDriverRepository driverRepository;

    @Mock
    private IRideRepository rideRepository;

    @Mock
    private IRiderRepository riderRepository;

    @Mock
    private IMatchRepository matchRepository;

    @InjectMocks
    private RiderService riderService;

    @Test
    void testAddDriver() throws RiderException {
        // Arrange
        String driverId = "D1";
        double x = 1.0;
        double y = 1.0;
        Driver driver = new Driver(driverId, x, y);
        when(driverRepository.getById(driverId)).thenReturn(null);
        doNothing().when(driverRepository).save(driver);
        // Act
        riderService.addDriver(driverId, x, y);
        // Assert
        verify(driverRepository, times(1)).getById(driverId);
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void testAddDriverThrowException() throws RiderException {
        // Arrange
        String driverId = "D1";
        double x = 1.0;
        double y = 1.0;
        Driver driver = new Driver(driverId, x, y);
        when(driverRepository.getById(driverId)).thenReturn(driver);
        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.addDriver(driverId, x, y));
        verify(driverRepository, times(1)).getById(driverId);
    }

    @Test
    void testAddRider() throws RiderException {
        // Arrange
        String riderId = "R1";
        double x = 1.0;
        double y = 1.0;
        Rider rider = new Rider(riderId, x, y);
        when(riderRepository.getById(riderId)).thenReturn(null);
        doNothing().when(riderRepository).save(rider);

        // Act
        riderService.addRider(riderId, x, y);

        // Assert
        verify(riderRepository, times(1)).getById(riderId);
        verify(riderRepository, times(1)).save(rider);
    }

    @Test
    void testAddRiderThrowException() throws RiderException {
        // Arrange
        String riderId = "R1";
        double x = 1.0;
        double y = 1.0;
        Rider rider = new Rider(riderId, x, y);
        when(riderRepository.getById(riderId)).thenReturn(rider);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.addRider(riderId, x, y));
        verify(riderRepository, times(1)).getById(riderId);
    }

    @Test
    void testMatch() throws RiderException {
        // Arrange
        String riderId = "R1";

        Rider rider = new Rider(riderId, 0.0, 0.0);

        Driver driver1 = new Driver("D1", 1, 1);
        Driver driver2 = new Driver("D2", 4, 5);
        Driver driver3 = new Driver("D3", 2, 2);

        List<Driver> drivers = List.of(driver1, driver2, driver3);
        List<String> matchedDrivers = List.of("D1", "D3");

        when(riderRepository.getById(riderId)).thenReturn(rider);
        when(driverRepository.getAll()).thenReturn(drivers);
        doNothing().when(matchRepository).save(any(Match.class));

        // Act
        List<String> result = riderService.match(riderId);

        // Assert
        verify(riderRepository, times(1)).getById(riderId);
        verify(driverRepository, times(1)).getAll();
        verify(matchRepository, times(1)).save(any(Match.class));
        Assertions.assertEquals(matchedDrivers, result);
    }

    @Test
    void testMatchThrowsExceptionWhenNoDrivers() throws RiderException {
        // Arrange
        String riderId = "R1";
        Rider rider = new Rider(riderId, 0.0, 0.0);

        Driver driver1 = new Driver("D1", 12, 11);
        Driver driver2 = new Driver("D2", 14, 15);

        List<Driver> drivers = List.of(driver1, driver2);

        when(riderRepository.getById(riderId)).thenReturn(rider);
        when(driverRepository.getAll()).thenReturn(drivers);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.match(riderId));
        verify(riderRepository, times(1)).getById(riderId);
        verify(driverRepository, times(1)).getAll();
    }

    @Test
    void testMatchThrowsExceptionWhenRideIsNull() throws RiderException {
        // Arrange
        String riderId = "R1";
        when(riderRepository.getById(riderId)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.match(riderId));
        verify(riderRepository, times(1)).getById(riderId);
    }

    @Test
    void testStartRideThrowsExceptionWhenNoMatch() {
        // Arrange
        String riderId = "R1";
        String rideId = "RIDE-001";
        int driverOption = 1;
        when(matchRepository.getById(riderId)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.startRide(rideId, driverOption, riderId));
        verify(matchRepository, times(1)).getById(riderId);
    }

    @Test
    void testStartRideThrowsExceptionWhenRideExists() {
        // Arrange
        String riderId = "R1";
        String driverId = "D3";
        String rideId = "RIDE-001";
        int driverOption = 1;

        Rider rider = new Rider(riderId, 0, 0);
        Driver driver = new Driver(driverId, 2, 2);

        Match match = new Match(riderId, List.of("D1", "D3"));

        Ride ride = new Ride(rideId, rider, driver, rider.getX(), rider.getY());

        when(matchRepository.getById(riderId)).thenReturn(match);
        when(rideRepository.getById(rideId)).thenReturn(ride);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.startRide(rideId, driverOption, riderId));
        verify(matchRepository, times(1)).getById(riderId);
        verify(rideRepository, times(1)).getById(rideId);
    }

    @Test
    void testStartRideThrowsExceptionWhenInvalidDriverOption() {
        // Arrange
        String riderId = "R1";
        String rideId = "RIDE-001";
        int driverOption = 4;

        Match match = new Match(riderId, List.of("D1", "D3"));

        when(matchRepository.getById(riderId)).thenReturn(match);
        when(rideRepository.getById(rideId)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.startRide(rideId, driverOption, riderId));
        verify(matchRepository, times(1)).getById(riderId);
        verify(rideRepository, times(1)).getById(rideId);
    }

    @Test
    void testStartRideThrowsExceptionWhenDriverIsNull() {
        // Arrange
        String riderId = "R1";
        String rideId = "RIDE-001";
        int driverOption = 2;

        Match match = new Match(riderId, List.of("D1", "D3"));

        when(matchRepository.getById(riderId)).thenReturn(match);
        when(rideRepository.getById(rideId)).thenReturn(null);
        when(driverRepository.getById(anyString())).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.startRide(rideId, driverOption, riderId));
        verify(matchRepository, times(1)).getById(riderId);
        verify(rideRepository, times(1)).getById(rideId);
        verify(driverRepository, times(1)).getById(anyString());
    }

    @Test
    void testStartRideThrowsExceptionWhenDriverUnAvailable() {
        // Arrange
        String riderId = "R1";
        String rideId = "RIDE-001";
        String driverId = "D3";
        int driverOption = 2;

        Driver driver = new Driver(driverId, 2, 2);
        driver.setAvailable(false);

        Match match = new Match(riderId, List.of("D1", "D3"));

        when(matchRepository.getById(riderId)).thenReturn(match);
        when(rideRepository.getById(rideId)).thenReturn(null);
        when(driverRepository.getById(anyString())).thenReturn(driver);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.startRide(rideId, driverOption, riderId));
        verify(matchRepository, times(1)).getById(riderId);
        verify(rideRepository, times(1)).getById(rideId);
        verify(driverRepository, times(1)).getById(anyString());
    }

    @Test
    void testStartRideThrowsExceptionWhenRiderIsNull() {
        // Arrange
        String riderId = "R1";
        String rideId = "RIDE-001";
        String driverId = "D3";
        int driverOption = 2;

        Driver driver = new Driver(driverId, 2, 2);

        Match match = new Match(riderId, List.of("D1", "D3"));

        when(matchRepository.getById(riderId)).thenReturn(match);
        when(rideRepository.getById(rideId)).thenReturn(null);
        when(driverRepository.getById(anyString())).thenReturn(driver);
        when(riderRepository.getById(riderId)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.startRide(rideId, driverOption, riderId));
        verify(matchRepository, times(1)).getById(riderId);
        verify(rideRepository, times(1)).getById(rideId);
        verify(driverRepository, times(1)).getById(anyString());
        verify(riderRepository, times(1)).getById(riderId);
    }

    @Test
    void testStartRide() throws RiderException {
        // Arrange
        String rideId = "RIDE-001";
        int driverOption = 2;
        String riderId = "R1";
        String driverId = "D3";

        Match match = new Match(riderId, List.of("D1", "D3"));
        Rider rider = new Rider(riderId, 0, 0);
        Driver driver = new Driver(driverId, 2, 2);

        when(matchRepository.getById(riderId)).thenReturn(match);
        when(rideRepository.getById(rideId)).thenReturn(null);
        when(driverRepository.getById(anyString())).thenReturn(driver);
        when(riderRepository.getById(riderId)).thenReturn(rider);
        doNothing().when(driverRepository).save(driver);
        doNothing().when(rideRepository).save(any(Ride.class));

        // Act
        String result = riderService.startRide(rideId, driverOption, riderId);

        // Assert
        Assertions.assertEquals(rideId, result);
        verify(matchRepository, times(1)).getById(riderId);
        verify(rideRepository, times(1)).getById(rideId);
        verify(driverRepository, times(1)).getById(anyString());
        verify(riderRepository, times(1)).getById(riderId);
        verify(driverRepository, times(1)).save(driver);
        verify(rideRepository, times(1)).save(any(Ride.class));
    }

    @Test
    void testStopRideThrowsExceptionWhenRideIsNull() {
        // Arrange
        String rideId = "RIDE-001";
        double x = 4;
        double y = 5;
        double minutes = 32;
        when(rideRepository.getById(rideId)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.stopRide(rideId, x, y, minutes));
        verify(rideRepository, times(1)).getById(rideId);
    }

    @Test
    void testStopRideThrowsExceptionWhenRideIsOver() {
        // Arrange
        String rideId = "RIDE-001";
        String riderId = "R1";
        String driverId = "D3";
        double x = 4;
        double y = 5;
        double minutes = 32;

        Rider rider = new Rider(riderId, 0, 0);
        Driver driver = new Driver(driverId, 2, 2);

        Ride ride = new Ride(rideId, rider, driver, rider.getX(), rider.getY());
        ride.stopRide(x, y, minutes);

        when(rideRepository.getById(rideId)).thenReturn(ride);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.stopRide(rideId, x, y, minutes));
        verify(rideRepository, times(1)).getById(rideId);
    }

    @Test
    void testStopRide() throws RiderException {
        // Arrange
        String rideId = "RIDE-001";
        String riderId = "R1";
        String driverId = "D3";
        double x = 4;
        double y = 5;
        double minutes = 32;

        Rider rider = new Rider(riderId, 0, 0);
        Driver driver = new Driver(driverId, 2, 2);

        Ride ride = new Ride(rideId, rider, driver, rider.getX(), rider.getY());

        when(rideRepository.getById(rideId)).thenReturn(ride);
        doNothing().when(driverRepository).save(driver);
        doNothing().when(rideRepository).save(ride);

        // Act
        String result = riderService.stopRide(rideId, x, y, minutes);

        // Assert
        Assertions.assertEquals(rideId, result);
        verify(rideRepository, times(1)).getById(rideId);
        verify(driverRepository, times(1)).save(driver);
        verify(rideRepository, times(1)).save(ride);
    }

    @Test
    void testBillThrowsExceptionWhenRideIsNull() {
        // Arrange
        String rideId = "RIDE-001";
        when(rideRepository.getById(rideId)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.bill(rideId));
        verify(rideRepository, times(1)).getById(rideId);
    }

    @Test
    void testBillThrowsExceptionWhenRideIsNotOver() {
        // Arrange
        String rideId = "RIDE-001";
        String riderId = "R1";
        String driverId = "D3";

        Rider rider = new Rider(riderId, 0, 0);
        Driver driver = new Driver(driverId, 2, 2);

        Ride ride = new Ride(rideId, rider, driver, rider.getX(), rider.getY());

        when(rideRepository.getById(rideId)).thenReturn(ride);

        // Act & Assert
        Assertions.assertThrows(RiderException.class, () -> riderService.bill(rideId));
        verify(rideRepository, times(1)).getById(rideId);
    }

    @Test
    void testBill() throws RiderException {
        // Arrange
        String rideId = "RIDE-001";
        String riderId = "R1";
        String driverId = "D3";
        double x = 4;
        double y = 5;
        double minutes = 32;
        double totalAmount = 186.72;

        Rider rider = new Rider(riderId, 0, 0);
        Driver driver = new Driver(driverId, 2, 2);

        Ride ride = new Ride(rideId, rider, driver, rider.getX(), rider.getY());
        ride.stopRide(x, y, minutes);

        BillResponse expectedBillResponse = new BillResponse(rideId, driverId, totalAmount);
        
        when(rideRepository.getById(rideId)).thenReturn(ride);

        BillResponse billResponse = riderService.bill(rideId);

        // Act & Assert
        Assertions.assertEquals(expectedBillResponse, billResponse);
        verify(rideRepository, times(1)).getById(rideId);
    }
}
