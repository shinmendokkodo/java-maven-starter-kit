package com.ridesharing.geektrust.commands;

import java.util.List;

import com.ridesharing.geektrust.commands.interfaces.ICommand;
import com.ridesharing.geektrust.dtos.BillResponse;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.services.IRiderService;
import com.ridesharing.geektrust.utilities.Constants;

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
