package org.shinmen.geektrust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.shinmen.geektrust.commands.CommandInvoker;
import org.shinmen.geektrust.configs.AppConfig;
import org.shinmen.geektrust.exceptions.RiderException;
import org.shinmen.geektrust.utilities.Constants;

public class Main {
    
    public static void main(String[] args) {
        List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        for (String commandLineArg : commandLineArgs) {
            run(commandLineArg);
        }
    }

    public static void run(String commandLineArg) {
        AppConfig applicationConfig = new AppConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(commandLineArg));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                    commandInvoker.executeCommand(tokens.get(Constants.FIRST), tokens);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException | RiderException e) {
            e.printStackTrace();
        }
    }
}
