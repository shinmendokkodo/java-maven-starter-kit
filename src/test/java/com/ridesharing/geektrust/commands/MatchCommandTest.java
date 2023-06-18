package com.ridesharing.geektrust.commands;

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
class MatchCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRiderService riderService;

    @InjectMocks
    private MatchCommand matchCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testExecute() throws Exception {
        // Arrange
        String riderId = "R1";
        List<String> tokens = Arrays.asList(Constants.MATCH_COMMAND, riderId);
        List<String> driverIds = List.of("D1", "D2", "D3");
        when(riderService.match(riderId)).thenReturn(driverIds);

        // Act
        matchCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.DRIVERS_MATCHED_OUTPUT + " " + String.join(" ", driverIds);
        verify(riderService, times(1)).match(riderId);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testExecuteWhenMatchThrowsRiderException() throws Exception {
        // Arrange
        String riderId = "R1";
        List<String> tokens = Arrays.asList(Constants.MATCH_COMMAND, riderId);
        doThrow(new RiderException(Constants.NO_DRIVERS_AVAILABLE_MESSAGE)).when(riderService).match(riderId);

        // Act
        matchCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.NO_DRIVERS_AVAILABLE_MESSAGE;
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(riderService ,times(1)).match(riderId);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}