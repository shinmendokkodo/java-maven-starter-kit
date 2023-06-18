package com.ridesharing.geektrust.commands.interfaces;

import java.util.List;

public interface ICommand {
    void execute(List<String> tokens);
}