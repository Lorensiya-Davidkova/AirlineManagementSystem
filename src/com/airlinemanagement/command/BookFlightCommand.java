package com.airlinemanagement.command;

import com.airlinemanagement.model.Flight;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;


public class BookFlightCommand implements Command{
    private Repository<Passenger> passengerRepository;
    private ConsoleView view;
    private FlightRepository flightRepository;
    private Passenger bookingPassenger;
    private Flight bookedFlight;

    public BookFlightCommand(Repository<Passenger> repo, ConsoleView view, FlightRepository repository){
        this.passengerRepository=repo;
        this.view=view;
        this.flightRepository=repository;
    }


        @Override
        public void execute()  {
            int id = view.getUserId();
            Passenger passenger = passengerRepository.findById(id);

            if (passenger == null) {
                view.showErrorMessage(" No such passenger, check ID please!");
                return;
            }

            this.bookingPassenger = passenger;

            String number = view.getFlightNumber();
            Flight flight = flightRepository.findFlight(number);

            if (flight == null) {
                view.showErrorMessage(" No such flight, check flight number!");
                return;
            }
            if (passenger.bookFlight(flight)) {
                this.bookedFlight = flight;
            }
        }

        @Override
        public void undo() {
            if (bookingPassenger == null || bookedFlight == null) {
                view.showWarningMessage(" No booking to undo.");
                return;
            }

            Passenger passenger = passengerRepository.findById(bookingPassenger.getId());

            if (passenger == null) {
                view.showErrorMessage("Passenger not found in the system.");
                return;
            }

            if (!passenger.getMyFlights().contains(bookedFlight)) {
                view.showWarningMessage("Passenger has not booked this flight. Nothing to undo.");
                return;
            }

            bookedFlight.cancelSeat();
            passenger.getMyFlights().remove(bookedFlight);
            view.showWarningMessage("Undo: Flight booking removed for " + passenger.getFirstName() + " " + passenger.getLastName());
        }
    }

