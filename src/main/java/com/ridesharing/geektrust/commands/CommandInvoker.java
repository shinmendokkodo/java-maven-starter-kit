package com.ridesharing.geektrust.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ridesharing.geektrust.commands.interfaces.ICommand;
import com.ridesharing.geektrust.exceptions.RiderException;
import com.ridesharing.geektrust.utilities.Constants;

public class CommandInvoker {
    private static final Map<String, ICommand> commandMap = new HashMap<>();

    public void register(String commandName, ICommand command){
        commandMap.put(commandName,command);
    }

    private ICommand get(String commandName){
        return commandMap.get(commandName);
    }

    public void executeCommand(String commandName, List<String> tokens) throws RiderException {
        ICommand command = get(commandName);
        if(command == null){
            throw new RiderException(Constants.NO_SUCH_COMMAND_MESSAGE);
        }
        command.execute(tokens);
    }
}
