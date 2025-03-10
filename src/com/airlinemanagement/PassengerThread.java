package com.airlinemanagement;

import com.airlinemanagement.model.Flight;

public class PassengerThread extends Thread {
    private final Flight flight;

    public PassengerThread(String name, Flight flight) {
        super(name); // Името на нишката ще бъде името на пътника
        this.flight = flight;
    }

    @Override
    public void run() {
        boolean success = flight.bookSeat();
        if (success) {
            System.out.println(getName() + " successfully booked a flight!");
        } else {
            System.out.println(getName() + " couldn't book a flight.");
        }
    }
    public static void main(String[] args){
        Flight flight = new Flight("A123", "New York", "London", "14:30");

        // 🔥Създаваме 5 нишки (повече от наличните места)
        PassengerThread p1 = new PassengerThread("Alice", flight);
        PassengerThread p2 = new PassengerThread("Bob", flight);
        PassengerThread p3 = new PassengerThread("Charlie", flight);
        PassengerThread p4 = new PassengerThread("David", flight);
        PassengerThread p5 = new PassengerThread("Eve", flight);

        //  Стартираме нишките едновременно
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}
