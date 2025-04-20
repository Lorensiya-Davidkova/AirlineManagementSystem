package com.airlinemanagement.model;

import com.airlinemanagement.Status;
import com.airlinemanagement.view.ConsoleView;

import java.util.HashSet;
import java.util.Set;

public class Passenger extends User {
    private Set<Flight> myFlights;
    /*transient казва на Gson да игнорира view при сериализация/десериализация,
     така че то няма да търси това поле в JSON файла.*/

    public Passenger(String firstName, String lastName, String telephoneNumber, String email) {
        super(firstName, lastName, telephoneNumber, email);
        this.myFlights = new HashSet<>();
    }

    public Passenger(int id, String firstName, String lastName, String telephoneNumber, String email) {
        super(id, firstName, lastName, telephoneNumber, email);
    }

    public Set<Flight> getMyFlights() {
        return myFlights;
    }

    public Status bookFlight(Flight flight) {
        if (!myFlights.add(flight)) {
            return Status.warning("You have already booked this flight: " + flight.getFlightNumber());
        }
        if (!flight.bookSeat()) { // Проверяваме дали има налични места
            myFlights.remove(flight); // Премахваме от списъка, защото няма място
            return Status.warning("No available seats for flight: " + flight.getFlightNumber());
        }
        return Status.success("Flight booked successfully: " + flight.getFlightNumber());
    }

    @Override
    public String toString() {
        return super.toString() + ", Flights: " + myFlights.size();
    }

    @Override
    public User clone() {
        return new Passenger(getId(), getFirstName(), getLastName(), getTelephoneNumber(), getEmail());
    }

   /*public void restoreState(User previousState) {
        super.restoreState(previousState);
    }*/
    public void onDelete(){
        System.out.println("Poletite na putnika sa: "+myFlights.size());
        for(Flight flight:myFlights){
            flight.cancelSeat();
        }
    }

    @Override
    public Set<Flight> getFlights() {
        return myFlights;
    }

}
