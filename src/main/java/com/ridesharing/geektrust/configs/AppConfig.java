package com.ridesharing.geektrust.configs;

import java.util.HashMap;

import com.ridesharing.geektrust.commands.AddDriverCommand;
import com.ridesharing.geektrust.commands.AddRiderCommand;
import com.ridesharing.geektrust.commands.BillCommand;
import com.ridesharing.geektrust.commands.CommandInvoker;
import com.ridesharing.geektrust.commands.MatchCommand;
import com.ridesharing.geektrust.commands.StartRideCommand;
import com.ridesharing.geektrust.commands.StopRideCommand;
import com.ridesharing.geektrust.repository.DriverRepository;
import com.ridesharing.geektrust.repository.MatchRepository;
import com.ridesharing.geektrust.repository.RideRepository;
import com.ridesharing.geektrust.repository.RiderRepository;
import com.ridesharing.geektrust.services.RiderService;
import com.ridesharing.geektrust.utilities.Constants;

public class AppConfig {
    private final RideRepository rideRepository = new RideRepository(new HashMap<>());
    private final RiderRepository riderRepository = new RiderRepository(new HashMap<>());
    private final MatchRepository matchRepository = new MatchRepository(new HashMap<>());
    private final DriverRepository driverRepository = new DriverRepository(new HashMap<>());

    private final RiderService riderService = new RiderService(rideRepository, driverRepository, matchRepository, riderRepository);

    private final AddDriverCommand addDriverCommand = new AddDriverCommand(riderService);
    private final AddRiderCommand addRiderCommand  = new AddRiderCommand(riderService);
    private final MatchCommand matchCommand = new MatchCommand(riderService);
    private final StartRideCommand startRideCommand = new StartRideCommand(riderService);
    private final StopRideCommand stopRideCommand = new StopRideCommand(riderService);
    private final BillCommand billCommand = new BillCommand(riderService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

     public CommandInvoker getCommandInvoker(){
        commandInvoker.register(Constants.ADD_DRIVER_COMMAND, addDriverCommand);
        commandInvoker.register(Constants.ADD_RIDER_COMMAND, addRiderCommand);
        commandInvoker.register(Constants.MATCH_COMMAND, matchCommand);
        commandInvoker.register(Constants.START_RIDE_COMMAND, startRideCommand);
        commandInvoker.register(Constants.STOP_RIDE_COMMAND, stopRideCommand);
        commandInvoker.register(Constants.BILL_COMMAND, billCommand);
        return commandInvoker;
    }
}
