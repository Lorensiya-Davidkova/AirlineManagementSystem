package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.StatusType;
import com.airlinemanagement.model.Flight;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;


public class BookFlightCommand implements UndoableCommand{
    private UserRepository<Passenger> passengerRepository;
    private ConsoleView view;
    private FlightRepository flightRepository;
    private Passenger bookingPassenger;
    private Flight bookedFlight;

    public BookFlightCommand(UserRepository<Passenger> repo, ConsoleView view, FlightRepository repository){
        this.passengerRepository=repo;
        this.view=view;
        this.flightRepository=repository;
    }


        @Override
        public Status execute()  {
            int id = view.getUserId();
            Passenger passenger = passengerRepository.findById(id);

            if (passenger == null) {
                return Status.error(" No such passenger, check ID please!");
            }

            this.bookingPassenger = passenger;

            String number = view.getFlightNumber();
            Flight flight = flightRepository.findFlight(number);

            if (flight == null) {
                return Status.error("No such flight, check flight number!");
            }
            Status status=passenger.bookFlight(flight);
            if (status.getType().equals(StatusType.SUCCESS)) {
                this.bookedFlight = flight;
                return Status.success("Thank you for booking flight: "+flight.getFlightNumber());
            }else{
                return status;
            }
        }

        @Override
        public Status undo() {
            if (bookingPassenger == null || bookedFlight == null) {
                return Status.warning(" No booking to undo.");
            }

            Passenger passenger = passengerRepository.findById(bookingPassenger.getId());

            if (passenger == null) {
                return Status.error("Passenger not found in the system.");
            }

            if (!passenger.getMyFlights().contains(bookedFlight)) {
                return Status.warning("Passenger has not booked this flight. Nothing to undo.");
            }

            bookedFlight.cancelSeat();
            passenger.getMyFlights().remove(bookedFlight);
            return Status.success("Undo: Flight booking removed for " + passenger.getFirstName() + " " + passenger.getLastName());
        }
    }

