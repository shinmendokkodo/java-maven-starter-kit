package org.shinmen.geektrust.commands;

import org.shinmen.geektrust.services.IRiderService;

public abstract class BaseCommand {

    protected final IRiderService riderService;

    protected BaseCommand(IRiderService riderService) {
        this.riderService = riderService;
    }

}
