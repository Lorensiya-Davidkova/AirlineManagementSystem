package com.airlinemanagement.repository;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonFlightRepository implements FlightRepository {
    private Set<Flight> flights;
    private final String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonFlightRepository(String filePath) throws IOException {
        this.filePath = filePath;
        this.flights = Collections.synchronizedSet(new HashSet<>());
        load();
    }

    @Override
    public Status addFlight(Flight flight) {
        synchronized (flights) {
            if (flights.add(flight)) {
                return save();
            } else {
                return Status.warning("Flight already exists!");
            }
        }
    }

    @Override
    public Result<Set<Flight>> listAllFlights() {
        synchronized (flights) {
            if (flights.isEmpty()) {
                return Result.warning(new HashSet<>(), "No flights available!");
            }
            return Result.success(new HashSet<>(flights), "Flights listed successfully.");
        }
    }

    @Override
    public Flight findFlight(String flightNumber) {
        synchronized (flights) {
            return flights.stream()
                    .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public Set<Flight> getFlights() {
        return flights;
    }

    private void load() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            Type setType = new TypeToken<Set<Flight>>() {}.getType();
            Set<Flight> loaded = gson.fromJson(reader, setType);
            if (loaded != null) {
                synchronized (flights) {
                    flights.addAll(loaded);
                    syncIds(loaded);
                }
            }
        }
    }

    private Status save() {
        synchronized (flights) {
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(flights, writer);
                return Status.success("Flight saved to file.");
            } catch (IOException e) {
                return Status.error("Error saving flight to file: " + e.getMessage());
            }
        }
    }

    private void syncIds(Set<Flight> loaded) {
        int maxId = loaded.stream().mapToInt(Flight::getFlightId).max().orElse(0);
        Flight.syncNextFlightId(maxId);
    }
}
