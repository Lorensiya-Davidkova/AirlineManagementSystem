package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.InMemoryFlightRepository;
import com.airlinemanagement.view.ConsoleView;

public class AddFlightCommand implements UndoableCommand{
    private FlightRepository flightRepository;
    private ConsoleView consoleView;
    private Flight lastAdded;

    public AddFlightCommand(FlightRepository repo, ConsoleView view){
        this.flightRepository=repo;
        this.consoleView=view;

    }
    @Override
    public Status execute() {
        Flight flight = consoleView.getFlightDetails();
        Status status=flightRepository.addFlight(flight);
        lastAdded=flight;
        return status;
    }

    @Override
    public String getDisplayText() {
        return ("│11. ✈️  Add new flight                       │");
       // return (" ✈️  Add new flight");
    }

    @Override
    public Status undo() {
        if (lastAdded != null) {
            boolean removed = flightRepository.getFlights().remove(lastAdded);
            if (removed) {
                flightRepository.persist();
                return Status.success("Flight addition undone: "+lastAdded.getFlightNumber());
            } else {
                return Status.warning("Flight not found.");
            }
        }
        return Status.error("No undo for this flight addition supported!");
    }

    @Override
    public String getUndoDisplayText() {
        return "Undo add flight: "+lastAdded.getFlightNumber();
    }
}
