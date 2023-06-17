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
class StopRideCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRiderService riderService;

    @InjectMocks
    private StopRideCommand stopRideCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testExecute() throws Exception {
        // Arrange
        String rideId = "RIDE-101";
        double x = 1.0;
        double y = 1.0;
        double timeTaken = 10.0;

        List<String> tokens = Arrays.asList(Constants.STOP_RIDE_COMMAND, rideId, String.valueOf(x), String.valueOf(y), String.valueOf(timeTaken));

        when(riderService.stopRide(rideId, x, y, timeTaken)).thenReturn(rideId);

        // Act
        stopRideCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.RIDE_STOPPED_OUTPUT + " " + rideId;
        verify(riderService, times(1)).stopRide(rideId, x, y, timeTaken);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testExecuteWhenStopRideThrowsRiderException() throws Exception {
        // Arrange
        String rideId = "RIDE-101";
        double x = 1.0;
        double y = 1.0;
        double timeTaken = 10.0;

        List<String> tokens = Arrays.asList(Constants.STOP_RIDE_COMMAND, rideId, String.valueOf(x), String.valueOf(y), String.valueOf(timeTaken));

        doThrow(new RiderException(Constants.INVALID_RIDE_MESSAGE)).when(riderService).stopRide(rideId, x, y, timeTaken);
        
        // Act
        stopRideCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.INVALID_RIDE_MESSAGE;
        verify(riderService, times(1)).stopRide(rideId, x, y, timeTaken);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}