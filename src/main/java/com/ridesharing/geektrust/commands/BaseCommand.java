package com.ridesharing.geektrust.commands;

import com.ridesharing.geektrust.services.IRiderService;

public abstract class BaseCommand {

    protected final IRiderService riderService;

    protected BaseCommand(IRiderService riderService) {
        this.riderService = riderService;
    }

}
