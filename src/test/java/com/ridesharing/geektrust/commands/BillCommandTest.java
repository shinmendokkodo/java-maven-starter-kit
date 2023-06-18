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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ridesharing.geektrust.dtos.BillResponse;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.services.IRiderService;
import com.ridesharing.geektrust.utilities.Constants;

@ExtendWith(MockitoExtension.class)
class BillCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRiderService riderService;

    @InjectMocks
    private BillCommand billCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testExecute() throws Exception {
        // Arrange
        String rideId = "RIDE-101";
        List<String> tokens = Arrays.asList(Constants.BILL_COMMAND, rideId);
        
        double totalAmount = 75.0;

        BillResponse billResponse = new BillResponse(rideId, "D1", totalAmount);
        when(riderService.bill(rideId)).thenReturn(billResponse);

        // Act
        billCommand.execute(tokens);

        // Assert
        String expectedOutput = "BILL RIDE-101 D1 75.00";
        verify(riderService, times(Constants.ONE)).bill(rideId);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void testExecuteWhenBillThrowsRiderException() throws Exception {
        // Arrange
        String rideId = "RIDE-101";
        List<String> tokens = Arrays.asList(Constants.BILL_COMMAND, rideId);

        doThrow(new RiderException(Constants.RIDE_NOT_COMPLETED_MESSAGE)).when(riderService).bill(rideId);

        // Act
        billCommand.execute(tokens);

        // Assert
        String expectedOutput = Constants.RIDE_NOT_COMPLETED_MESSAGE;
        verify(riderService, times(Constants.ONE)).bill(rideId);
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}