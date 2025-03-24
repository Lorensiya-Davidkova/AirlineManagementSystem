package com.airlinemanagement.repository;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;

import java.util.HashSet;
import java.util.Set;

public class FlightRepository {
    private Set<Flight> flights = new HashSet<>();

    public Status addFlight(Flight flight) {
        if (flights.add(flight)) {
            return Status.success("🎉 Flight added successfully! 🎉");
        } else {
            return Status.warning("Flight already exists!");
        }
    }

    public Set<Flight> getFlights() {
        return flights;
    }
    public void setFlights(Set<Flight> flights){this.flights=flights;}

    public Flight findFlight(String flightNumber) {
        return flights.stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public Status listAllFlights() {
        if (flights.isEmpty()) {
            return Status.warning("No flights available!");
        } else {
           return Status.success(flights.toString());
        }
    }
}

