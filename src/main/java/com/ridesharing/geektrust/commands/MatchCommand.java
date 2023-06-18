package com.ridesharing.geektrust.commands;

import java.util.List;

import com.ridesharing.geektrust.commands.interfaces.ICommand;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.services.IRiderService;
import com.ridesharing.geektrust.utilities.Constants;

public class MatchCommand extends BaseCommand implements ICommand {
    public MatchCommand(IRiderService riderService) {
        super(riderService);
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String riderId = tokens.get(Constants.SECOND);
            
            List<String> driverIds = riderService.match(riderId);
            System.out.println(Constants.DRIVERS_MATCHED_OUTPUT + " " + String.join(" ", driverIds));
        } catch (RiderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
