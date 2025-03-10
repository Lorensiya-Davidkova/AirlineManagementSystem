package com.airlinemanagement.command;

import java.util.concurrent.ExecutionException;

public interface Command {
    void execute();
    void undo();
}
