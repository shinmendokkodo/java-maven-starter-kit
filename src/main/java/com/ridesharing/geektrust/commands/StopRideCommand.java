package com.ridesharing.geektrust.commands;

import java.util.List;

import com.ridesharing.geektrust.commands.interfaces.ICommand;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.services.IRiderService;
import com.ridesharing.geektrust.utilities.Constants;

public class StopRideCommand extends BaseCommand implements ICommand {
    public StopRideCommand(IRiderService riderService) {
        super(riderService);
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String rideId = tokens.get(Constants.SECOND);
            double x = Double.parseDouble(tokens.get(Constants.THIRD));
            double y = Double.parseDouble(tokens.get(Constants.FOURTH));
            double minutes = Double.parseDouble(tokens.get(Constants.FIFTH));
            
            String ride = riderService.stopRide(rideId, x, y, minutes);
            System.out.println(Constants.RIDE_STOPPED_OUTPUT + " " + ride);
        } catch (RiderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
