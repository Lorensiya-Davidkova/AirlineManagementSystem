package com.airlinemanagement.controller;

import com.airlinemanagement.Status;
import com.airlinemanagement.command.*;
import com.airlinemanagement.model.Employee;
import com.airlinemanagement.model.Flight;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AirlineController {
    private final Repository<Passenger> passengerRepository;
    private final Repository<Employee> employeeRepository;
    private final FlightRepository flightRepository;
    private final ConsoleView view;
    private final CommandManager commandManager;
    private final Map<Integer, Consumer<Status>> menuActions = new HashMap<>();

    public AirlineController() {
        this.view =new ConsoleView();
        this.passengerRepository = new Repository<>();
        this.employeeRepository = new Repository<>();
        this.flightRepository = new FlightRepository();
        this.commandManager = new CommandManager();
        initializeMenu();
    }

    private void initializeMenu() {
        menuActions.put(1, status -> executeCommand(new AddPassengerCommand(passengerRepository, view)));
        menuActions.put(2, status -> executeCommand(new ListAllUsersCommand<>(passengerRepository)));
        menuActions.put(3, status -> executeCommand(new EditUserCommand<>(passengerRepository, view)));
        menuActions.put(4, status -> executeCommand(new FindUserCommand<>(passengerRepository, view)));
        menuActions.put(5, status -> executeCommand(new DeleteUserCommand<>(passengerRepository, view)));
        menuActions.put(6, status -> executeCommand(new AddEmployeeCommand(employeeRepository, view)));
        menuActions.put(7, status -> executeCommand(new ListAllUsersCommand<>(employeeRepository)));
        menuActions.put(8, status -> executeCommand(new EditUserCommand<>(employeeRepository, view)));
        menuActions.put(9, status -> executeCommand(new FindUserCommand<>(employeeRepository, view)));
        menuActions.put(10, status -> executeCommand(new DeleteUserCommand<>(employeeRepository, view)));
        menuActions.put(11, status -> executeCommand(new AddFlightCommand(flightRepository, view)));
        menuActions.put(12, status -> executeCommand(new ListAllFlightsCommand(flightRepository)));
        menuActions.put(13, status -> executeCommand(new BookFlightCommand(passengerRepository, view, flightRepository)));
        menuActions.put(14, status -> executeUndo());
    }

    public void start() {
       chooseRepositoryType();

        boolean running = true;
        while (running) {
            String undoLabel = commandManager.getLastCommandName();
            int choice = view.showMainMenu(undoLabel);
            Consumer<Status> action = menuActions.get(choice);

            if (action != null) {
                action.accept(null); // Предавам `null`, защото `Consumer<Status>` изисква аргумент
            } else if (choice == 0) {
                running = false;
            } else {
                view.printInvalidChoice();
            }
        }
    }

    private void executeCommand(Command command) {
        Status status = commandManager.execute(command);
        printStatus(status);
    }

    private void executeUndo() {
        Status status = commandManager.undo();
        printStatus(status);
    }

    private void printStatus(Status status) {
        if (status == null) return;
        switch (status.getType()) {
            case SUCCESS -> view.showSuccessMessage(status.getMessage());
            case WARNING -> view.showWarningMessage(status.getMessage());
            case ERROR -> view.showErrorMessage(status.getMessage());
        }
    }
    public void chooseRepositoryType() {
        int choice = view.getRepositoryType();
        if (choice == 2) {
            passengerRepository.setUsers(passengerRepository.loadFromJson("/Users/macbookair/Desktop/AirlineManagmentSystem/src/com/airlinemanagement/passengers.json", Passenger.class));
            employeeRepository.setUsers(employeeRepository.loadFromJson("/Users/macbookair/Desktop/AirlineManagmentSystem/src/com/airlinemanagement/employees.json", Employee.class));
            flightRepository.setFlights(flightRepository.loadFromJson("/Users/macbookair/Desktop/AirlineManagmentSystem/src/com/airlinemanagement/flights.json", Flight.class));
            view.showSuccessMessage("Starting with full repositories.");
        } else if(choice==1) {
            view.showSuccessMessage("Starting with empty repositories. Add data manually.");
        }
    }
}



