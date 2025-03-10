package com.airlinemanagement.command;

import com.airlinemanagement.repository.FlightRepository;

public class ListAllFlightsCommand implements Command {
    private FlightRepository repository;
    public ListAllFlightsCommand(FlightRepository repo){
        this.repository=repo;
    }
    @Override
    public void execute() {
        repository.listAllFlights();
    }

    @Override
    public void undo() {
        System.out.println("⚠️ Undo not applicable for listing flights.");
    }
}
