package com.airlinemanagement.command;

import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.view.ConsoleView;

public class ListAllFlightsCommand implements Command {
    private FlightRepository repository;
    private ConsoleView view;
    public ListAllFlightsCommand(FlightRepository repo,ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public void execute() {
        repository.listAllFlights();
    }

    @Override
    public void undo() {
        view.showWarningMessage("️Undo not applicable for listing flights.");
    }
}
