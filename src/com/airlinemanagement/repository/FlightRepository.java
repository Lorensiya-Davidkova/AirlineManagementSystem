package com.airlinemanagement.repository;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;

import java.util.Set;

public interface FlightRepository {
    Status addFlight(Flight flight);
    Result<Set<Flight>> listAllFlights();
    Flight findFlight(String flightNumber);
    Set<Flight> getFlights();
    void persist();
}
