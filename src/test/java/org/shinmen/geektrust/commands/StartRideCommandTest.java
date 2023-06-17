package org.shinmen.geektrust.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shinmen.geektrust.exceptions.RiderException;
import org.shinmen.geektrust.services.IRiderService;
import org.shinmen.geektrust.utilities.Constants;

@ExtendWith(MockitoExtension.class)
class StartRideCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRiderService riderService;

    @InjectMocks
    private StartRideCommand startRideCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testExecute() throws Exception {
        // Arrange
        String rideId = "RIDE-101";
        String riderId = "R1";
        int driverOption = 1;
        
        List<String> tokens = Arrays.asList(Constants.START_RIDE_COMMAND, rideId, String.valueOf(driverOption), riderId);

        when(riderService.startRide(rideId, driverOption, riderId)).thenReturn(rideId);

        // Act
        startRideCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.RIDE_STARTED_OUTPUT + " " + rideId;
        verify(riderService, times(1)).startRide(rideId, driverOption, riderId);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testExecuteWhenStartRideThrowsRiderException() throws Exception {
        // Arrange
        String rideId = "RIDE-101";
        String riderId = "R1";
        int driverOption = 1;
        List<String> tokens = Arrays.asList(Constants.START_RIDE_COMMAND, rideId, String.valueOf(driverOption), riderId);

        doThrow(new RiderException(Constants.INVALID_RIDE_MESSAGE)).when(riderService).startRide(rideId, driverOption, riderId);
        
        // Act
        startRideCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.INVALID_RIDE_MESSAGE;
        verify(riderService, times(1)).startRide(rideId, driverOption, riderId);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}