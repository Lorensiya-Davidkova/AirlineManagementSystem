package com.airlinemanagement.command;

import com.airlinemanagement.Status;

public interface UndoableCommand extends Command{
    Status undo();
}
