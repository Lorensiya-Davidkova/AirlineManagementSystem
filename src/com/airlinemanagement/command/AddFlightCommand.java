package com.airlinemanagement.command;

import com.airlinemanagement.model.Flight;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.view.ConsoleView;

public class AddFlightCommand implements Command{
    private FlightRepository flightRepository;
    private ConsoleView consoleView;
    private Flight lastAdded;

    public AddFlightCommand(FlightRepository repo, ConsoleView view){
        this.flightRepository=repo;
        this.consoleView=view;

    }
    @Override
    public void execute() {
        Flight flight = consoleView.getFlightDetails();
        flightRepository.addFlight(flight);
        lastAdded=flight;
    }

    @Override
    public void undo() {
        if (lastAdded != null) {
            boolean removed = flightRepository.getFlights().remove(lastAdded);
            if (removed) {
                System.out.println("⚠️ Flight addition undone: " + lastAdded.getFlightNumber());
            } else {
                System.out.println("Flight not found.");
            }
        }
    }
}
