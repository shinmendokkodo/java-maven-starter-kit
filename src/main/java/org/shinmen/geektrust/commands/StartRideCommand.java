package org.shinmen.geektrust.commands;

import java.util.List;

import org.shinmen.geektrust.commands.interfaces.ICommand;
import org.shinmen.geektrust.exceptions.RiderException;
import org.shinmen.geektrust.services.IRiderService;
import org.shinmen.geektrust.utilities.Constants;

public class StartRideCommand extends BaseCommand implements ICommand {

    public StartRideCommand(IRiderService riderService) {
        super(riderService);
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String rideId = tokens.get(Constants.SECOND);
            int driverOption = Integer.parseInt(tokens.get(Constants.THIRD));
            String riderId = tokens.get(Constants.FOURTH);
            
            String ride = riderService.startRide(rideId, driverOption, riderId);
            System.out.println(Constants.RIDE_STARTED_OUTPUT + " " + ride);
        } catch (RiderException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
