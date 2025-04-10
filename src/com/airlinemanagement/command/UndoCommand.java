package com.airlinemanagement.command;

import com.airlinemanagement.Status;

public class UndoCommand implements Command{
    private CommandManager manager;
    public UndoCommand(CommandManager manager){
        this.manager=manager;
    }
    @Override
    public Status execute() {
       return manager.undo();
    }

    @Override
    public String getDisplayText() {
        return "14. ↩️"+manager.getLastCommandName();
    }
}
