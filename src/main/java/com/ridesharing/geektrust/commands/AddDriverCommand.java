package com.ridesharing.geektrust.commands;

import java.util.List;

import com.ridesharing.geektrust.commands.interfaces.ICommand;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.services.IRiderService;
import com.ridesharing.geektrust.utilities.Constants;

public class AddDriverCommand extends BaseCommand implements ICommand {
    public AddDriverCommand(IRiderService riderService) {
        super(riderService);
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String driverId = tokens.get(Constants.SECOND);
            double x = Double.parseDouble(tokens.get(Constants.THIRD));
            double y = Double.parseDouble(tokens.get(Constants.FOURTH));
            
            riderService.addDriver(driverId, x, y);
        } catch (RiderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
