package com.ridesharing.geektrust.commands;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.services.IRiderService;
import com.ridesharing.geektrust.utilities.Constants;

@ExtendWith(MockitoExtension.class)
class AddDriverCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRiderService riderService;

    @InjectMocks
    private AddDriverCommand addDriverCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testExecute() throws Exception {
        // Arrange
        String driverId = "D1";
        double x = 1.0;
        double y = 2.0;
        List<String> tokens = Arrays.asList(Constants.ADD_DRIVER_COMMAND, driverId, String.valueOf(x), String.valueOf(y));
        doNothing().when(riderService).addDriver(driverId, x, y);

        // Act
        addDriverCommand.execute(tokens);

        // Assert
        verify(riderService, times(Constants.ONE)).addDriver(driverId, x, y);
    }

    @Test
    void testExecuteWhenAddDriverThrowsRiderException() throws Exception {
        // Arrange
        String driverId = "D1";
        double x = 1.0;
        double y = 2.0;
        List<String> tokens = Arrays.asList(Constants.ADD_DRIVER_COMMAND, driverId, String.valueOf(x), String.valueOf(y));
        doThrow(new RiderException(Constants.ENTITY_EXISTS_MESSAGE)).when(riderService).addDriver(driverId, x, y);

        // Act
        addDriverCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.ENTITY_EXISTS_MESSAGE;
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(riderService ,times(Constants.ONE)).addDriver(driverId, x, y);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}