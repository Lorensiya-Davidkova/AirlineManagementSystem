package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.view.ConsoleView;

public class ListAllFlightsCommand implements Command {
    private FlightRepository repository;
    public ListAllFlightsCommand(FlightRepository repo){
        this.repository=repo;
    }
    @Override
    public Status execute() {
        return repository.listAllFlights();
    }


   /* @Override
    public Status undo() {
        return Status.warning("️Undo not applicable for listing flights.");
    }*/
}
