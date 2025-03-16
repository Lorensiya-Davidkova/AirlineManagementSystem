package com.airlinemanagement.repository;
import com.airlinemanagement.model.Flight;
import com.airlinemanagement.view.ConsoleView;

import java.util.HashSet;
import java.util.Set;

public class FlightRepository {
    private Set<Flight> flights = new HashSet<>();
    private ConsoleView view=new ConsoleView();

    public void addFlight(Flight flight) {
        if (flights.add(flight)) {
            view.showSuccessMessage("🎉 Flight added successfully! 🎉");
        } else {
            view.showWarningMessage("Flight already exists!");
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
            view.showWarningMessage("No flights available!");
        } else {
           view.printAllItems(flights);
        }
    }
}
/*
- Използваме Set вместо ArrayList, за да избегнем дублирани полети.
- Добавен е findFlight(String flightNumber) – по-оптимален начин за намиране на полет.
- Методът addFlight() проверява за дублиране.*/
