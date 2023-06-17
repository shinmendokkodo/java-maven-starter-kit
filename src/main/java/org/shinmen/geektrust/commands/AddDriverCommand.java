package org.shinmen.geektrust.commands;

import java.util.List;

import org.shinmen.geektrust.commands.interfaces.ICommand;
import org.shinmen.geektrust.exceptions.RiderException;
import org.shinmen.geektrust.services.IRiderService;
import org.shinmen.geektrust.utilities.Constants;

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
