package com.ridesharing.geektrust.utilities;

import java.text.DecimalFormat;

public class Constants {

    private Constants() {
    }

    public static final int BASE_FARE = 50;

    public static double distanceFare(double distanceTraveled) {
        return 6.5 * distanceTraveled;
    }

    public static double timeFare(double timeTaken) {
        return 2.0 * timeTaken;
    }

    public static double taxedFare(double fare) {
        return 0.2 * fare;
    }

    // COMMANDS
    public static final String ADD_DRIVER_COMMAND = "ADD_DRIVER";
    public static final String ADD_RIDER_COMMAND = "ADD_RIDER";
    public static final String MATCH_COMMAND = "MATCH";
    public static final String START_RIDE_COMMAND = "START_RIDE";
    public static final String STOP_RIDE_COMMAND = "STOP_RIDE";
    public static final String BILL_COMMAND = "BILL";

    // TOKEN INDEX
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIFTH = 4;

    // EXCEPTION MESSAGES
    public static final String NO_SUCH_COMMAND_MESSAGE = "NO_SUCH_COMMAND";
    public static final String ENTITY_EXISTS_MESSAGE = "ENTITY_EXISTS";
    public static final String ENTITY_NOT_FOUND_MESSAGE = "ENTITY_NOT_FOUND";
    public static final String NO_DRIVERS_AVAILABLE_MESSAGE = "NO_DRIVERS_AVAILABLE";
    public static final String INVALID_RIDE_MESSAGE = "INVALID_RIDE";
    public static final String RIDE_NOT_COMPLETED_MESSAGE = "RIDE_NOT_COMPLETED";

    // OUTPUT MESSAGES
    public static final String DRIVERS_MATCHED_OUTPUT = "DRIVERS_MATCHED";
    public static final String RIDE_STARTED_OUTPUT = "RIDE_STARTED";
    public static final String RIDE_STOPPED_OUTPUT = "RIDE_STOPPED";
    public static final String BILL_OUTPUT = "BILL";
    
    public static String formatNumber(double number) {
        String decimal = "0.00";
        DecimalFormat decimalFormat = new DecimalFormat(decimal);
        return decimalFormat.format(number);
    }
}
