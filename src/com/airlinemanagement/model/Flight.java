package com.airlinemanagement.model;
public class Flight {
    private static int nextFlightId = 1;
    private  int flightId;
    private String flightNumber;
    private String departure;
    private String destination;
    private String departureTime;
    private int availableSeats; // Общ ресурс, за който нишките ще се състезават

    public Flight(String flightNumber, String departure, String destination, String departureTime) {
        this.flightId = nextFlightId++;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats=3;

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
    public synchronized boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            System.out.println(Thread.currentThread().getName() + " booked a seat. Remaining seats: " + availableSeats);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " can't book a seat. No seats left.");
            return false;
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
/* Няма сетъри, тъй като полетът не би трябвало да се променя.*/
