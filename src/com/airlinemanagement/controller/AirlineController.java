package com.airlinemanagement.controller;

import com.airlinemanagement.FilePaths;
import com.airlinemanagement.Status;
import com.airlinemanagement.command.*;
import com.airlinemanagement.model.Employee;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.JsonUserRepository;
import com.airlinemanagement.repository.InMemoryUserRepository;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class AirlineController {
   private final Set<Passenger> passengerSet = new HashSet<>();
    private final Set<Employee> employeeSet = new HashSet<>();
    private  UserRepository<Passenger> passengerRepository;
    private  UserRepository<Employee> employeeRepository;
    private final FlightRepository flightRepository;
    private final ConsoleView view;
    private final CommandManager commandManager;
    private final Map<Integer, Runnable> menuActions = new HashMap<>();

    public AirlineController() {
        this.view =new ConsoleView();
        this.flightRepository = new FlightRepository();
        this.commandManager = new CommandManager();
        initializeMenu();
    }

    private void initializeMenu() {
        menuActions.put(1, () -> executeCommand(new AddPassengerCommand(passengerRepository, view)));
        menuActions.put(2, () -> executeCommand(new ListAllUsersCommand<>(passengerRepository)));
        menuActions.put(3, () -> executeCommand(new EditUserCommand<>(passengerRepository, view)));
        menuActions.put(4, () -> executeCommand(new FindUserCommand<>(passengerRepository, view)));
        menuActions.put(5, () -> executeCommand(new DeleteUserCommand<>(passengerRepository, view)));
        menuActions.put(6, () -> executeCommand(new AddEmployeeCommand(employeeRepository, view)));
        menuActions.put(7, () -> executeCommand(new ListAllUsersCommand<>(employeeRepository)));
        menuActions.put(8, () -> executeCommand(new EditUserCommand<>(employeeRepository, view)));
        menuActions.put(9, () -> executeCommand(new FindUserCommand<>(employeeRepository, view)));
        menuActions.put(10, () -> executeCommand(new DeleteUserCommand<>(employeeRepository, view)));
        menuActions.put(11, () -> executeCommand(new AddFlightCommand(flightRepository, view)));
        menuActions.put(12, () -> executeCommand(new ListAllFlightsCommand(flightRepository)));
        menuActions.put(13, () -> executeCommand(new BookFlightCommand(passengerRepository, view, flightRepository)));
        menuActions.put(14, this::executeUndo); // използване на метод референция
    }


    public void start() {
       chooseRepositoryType();

        boolean running = true;
        while (running) {
            String undoLabel = commandManager.getLastCommandName();
            int choice = view.showMainMenu(undoLabel);
            Runnable action = menuActions.get(choice);

            if (action != null) {
                action.run();
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
    private void chooseRepositoryType() {
        int choice = view.getRepositoryType();
        initializeRepositories(choice);
    }

    private void initializeRepositories(int choice) {
        if (choice == 2) {
            passengerRepository = new JsonUserRepository<>(
                    FilePaths.PASSENGERS, Passenger.class, passengerSet);
            employeeRepository = new JsonUserRepository<>(
                    FilePaths.EMPLOYEES, Employee.class, employeeSet);
            view.showSuccessMessage("Repositories loaded from JSON.");
        } else {
            passengerRepository = new InMemoryUserRepository<>(passengerSet);
            employeeRepository = new InMemoryUserRepository<>(employeeSet);
            view.showSuccessMessage("Starting with empty repositories.");
        }
    }

}



