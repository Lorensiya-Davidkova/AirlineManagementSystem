package com.airlinemanagement.command;

import com.airlinemanagement.Status;


public interface Command {
    Status execute();
    String getDisplayText();
}
