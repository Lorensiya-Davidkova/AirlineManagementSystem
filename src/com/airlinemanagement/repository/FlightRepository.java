package com.airlinemanagement.repository;
import com.airlinemanagement.model.Flight;

import java.util.HashSet;
import java.util.Set;

public class FlightRepository {
    private Set<Flight> flights = new HashSet<>();

    public void addFlight(Flight flight) {
        if (flights.add(flight)) {
            System.out.println("🎉 Flight added successfully! 🎉");
        } else {
            System.out.println("Flight already exists!");
        }
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public Flight findFlight(String flightNumber) {
        return flights.stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public void listAllFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available!");
        } else {
            flights.forEach(System.out::println);
        }
    }
}
/*
- Използваме Set вместо ArrayList, за да избегнем дублирани полети.
- Добавен е findFlight(String flightNumber) – по-оптимален начин за намиране на полет.
- Методът addFlight() проверява за дублиране.*/
