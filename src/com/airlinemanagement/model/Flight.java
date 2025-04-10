package com.airlinemanagement.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Flight {
    private static AtomicInteger nextFlightId=new AtomicInteger(1);
    private  int flightId;
    private String flightNumber;
    private String departure;
    private String destination;
    private String departureTime;
    private AtomicInteger availableSeats; // Общ ресурс, за който нишките ще се състезават

    public Flight() {}
    public Flight(String flightNumber, String departure, String destination, String departureTime) {
        this.flightId = nextFlightId.getAndIncrement();
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = new AtomicInteger(1);

    }
    public Flight(int flightId, String flightNumber, String departure, String destination, String departureTime, int availableSeats) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = new AtomicInteger(availableSeats);
    }

    public int getFlightId() {
        return flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    @Override
    public String toString() {
        return "Flight ID: " + flightId + ", Number: " + flightNumber + ", From: " + departure + " To: " + destination + ", Departure: " + departureTime;
    }
    public boolean bookSeat() {
        while (true) {
            int currentSeats = availableSeats.get();
            if (currentSeats > 0) {
                if (availableSeats.compareAndSet(currentSeats, currentSeats - 1)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public void cancelSeat() {
        availableSeats.incrementAndGet();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Flight flight = (Flight) obj;
        return Objects.equals(flightNumber, flight.flightNumber) &&
                Objects.equals(departure, flight.departure) &&
                Objects.equals(destination, flight.destination) &&
                Objects.equals(departureTime, flight.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, departure, destination, departureTime);
    }
    public static void syncNextFlightId(int value) {
        nextFlightId.set(value + 1);
    }
}

