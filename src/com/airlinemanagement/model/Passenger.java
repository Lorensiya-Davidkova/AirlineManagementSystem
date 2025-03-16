package com.airlinemanagement.model;

import com.airlinemanagement.view.ConsoleView;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Passenger extends User {
    private Set<Flight> myFlights;
    /*
    !Gson не може да сериализира обекти като Scanner, защото те не са стандартни POJO (Plain Old Java Objects).
    transient казва на Gson да игнорира това поле при сериализация/десериализация.
    След десериализация view ще бъде null, но можем да го инициализираме ръчно в конструктора.*/
    private transient ConsoleView view = new ConsoleView();

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

    public boolean bookFlight(Flight flight) {
       // this.view=new ConsoleView();
        if (!myFlights.add(flight)) {
            view.showWarningMessage("You have already booked this flight: " + flight.getFlightNumber());
            return false;
        }
        if (!flight.bookSeat()) { // Проверяваме дали има налични места
            myFlights.remove(flight); // Премахваме от списъка, защото няма място
            view.showWarningMessage("No available seats for flight: " + flight.getFlightNumber());
            return false;
        }
        view.showSuccessMessage("Flight booked successfully: " + flight.getFlightNumber());
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + ", Flights: " + myFlights.size();
    }

    @Override
    public User clone() {
        return new Passenger(getId(), getFirstName(), getLastName(), getTelephoneNumber(), getEmail());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Passenger passenger = (Passenger) obj;
        return this.getId() == passenger.getId(); // Сравняваме само по ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId()); // Използваме само ID
    }
    public void restoreState(User previousState) {
        super.restoreState(previousState);
    }
}
