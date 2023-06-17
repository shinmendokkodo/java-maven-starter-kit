package org.shinmen.geektrust.commands;

import java.util.List;

import org.shinmen.geektrust.commands.interfaces.ICommand;
import org.shinmen.geektrust.dtos.BillResponse;
import org.shinmen.geektrust.exceptions.RiderException;
import org.shinmen.geektrust.services.IRiderService;
import org.shinmen.geektrust.utilities.Constants;

public class BillCommand extends BaseCommand implements ICommand {

    public BillCommand(IRiderService riderService) {
        super(riderService);
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String rideId = tokens.get(Constants.SECOND);
            
            BillResponse bill = riderService.bill(rideId);
            System.out.println(Constants.BILL_OUTPUT + " " + bill);
        } catch (RiderException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
