package com.airlinemanagement.repository;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryFlightRepository implements FlightRepository{
    private Set<Flight> flights;

    public InMemoryFlightRepository(){
        flights= Collections.synchronizedSet(new HashSet<>());
    }
    public Status addFlight(Flight flight) {
        synchronized (flights) {
            if (flights.add(flight)) {
                return Status.success("🎉 Flight added successfully! 🎉");
            } else {
                return Status.warning("Flight already exists!");
            }
        }
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public Flight findFlight(String flightNumber) {
        synchronized (flights) {
            return flights.stream()
                    .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                    .findFirst()
                    .orElse(null);
        }
    }

    public Result<Set<Flight>> listAllFlights() {
        synchronized (flights) {
            if (flights.isEmpty()) {
                return Result.warning(new HashSet<>(), "No flights available!");
            } else {
                return Result.success(new HashSet<>(flights), "Flights listed successfully.");
            }
        }
    }
}

