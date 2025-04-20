package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.InMemoryFlightRepository;
import com.airlinemanagement.repository.Result;

import java.util.Set;

public class ListAllFlightsCommand implements Command {
    private FlightRepository repository;
    public ListAllFlightsCommand(FlightRepository repo){
        this.repository=repo;
    }
    @Override
    public Status execute() {
        Result<Set<Flight>> result = repository.listAllFlights();
        Set<Flight> flights = result.getData();
        if (flights.isEmpty()) {
            return Status.warning("No flights in the repository.");
        }

        StringBuilder sb = new StringBuilder("📋 Flights:\n");
        for (Flight flight : flights) {
            sb.append(" • ").append(flight).append("\n");
        }
        return Status.success(sb.toString());
    }

    @Override
    public String getDisplayText() {
        return "│12. 📋  List all flights                     │";
       // return " 📋  List all flights";
    }
}
