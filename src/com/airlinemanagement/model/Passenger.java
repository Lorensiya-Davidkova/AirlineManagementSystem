package com.airlinemanagement.model;

import java.util.HashSet;
import java.util.Set;

public class Passenger extends User {
    private Set<Flight> myFlights;

    public Passenger(String firstName, String lastName, String telephoneNumber, String email) {
        super(firstName, lastName, telephoneNumber, email);
        this.myFlights = new HashSet<>();
    }
    public Passenger(int id, String firstName, String lastName, String telephoneNumber, String email){
        super(id,firstName, lastName, telephoneNumber, email);
    }

    public Set<Flight> getMyFlights() {
        return myFlights;
    }

    public boolean isBookFlight(Flight flight) {
        return myFlights.add(flight);
    }

    @Override
    public String toString() {
        return super.toString() + ", Flights: " + myFlights.size();
    }

    @Override
    public User clone() {
        return new Passenger(getId(), getFirstName(), getLastName(), getTelephoneNumber(), getEmail());
    }
}
