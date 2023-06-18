package com.ridesharing.geektrust;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void mainRunTest() {
        // Assert
        String args = "sample_input/input1.txt";
        String expectedOutput = "DRIVERS_MATCHED D1 D3\n" +
                "RIDE_STARTED RIDE-001\n" +
                "RIDE_STOPPED RIDE-001\n" +
                "BILL RIDE-001 D3 186.72";
        // Act
        Main.run(args);
        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}