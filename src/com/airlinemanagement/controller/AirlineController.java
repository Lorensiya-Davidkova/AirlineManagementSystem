package com.airlinemanagement.controller;

import com.airlinemanagement.command.*;
import com.airlinemanagement.model.Employee;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

import java.util.HashMap;
import java.util.Map;


public class AirlineController {
    private Repository<Passenger> passengerRepository = new Repository<>();
    private Repository<Employee> employeeRepository = new Repository<>();
    private FlightRepository flightRepository = new FlightRepository();
    private ConsoleView view;
    private CommandManager command = new CommandManager();

    private final Map<Integer, Runnable> menuActions = new HashMap<>();

    public AirlineController(ConsoleView view) {
        this.view=view;
        initializeMenu();
    }

    private void initializeMenu() {
        menuActions.put(1, this::addPassenger);
        menuActions.put(2, this::listAllPassengers);
        menuActions.put(3, this::editPassenger);
        menuActions.put(4, this::findPassenger);
        menuActions.put(5, this::deletePassenger);
        menuActions.put(6, this::addEmployee);
        menuActions.put(7, this::listAllEmployees);
        menuActions.put(8, this::editEmployee);
        menuActions.put(9, this::findEmployee);
        menuActions.put(10, this::deleteEmployee);
        menuActions.put(11, this::addFlight);
        menuActions.put(12, this::listAllFlights);
        menuActions.put(13,this::bookFlight);
        menuActions.put(14,this::undo);

    }
    public void start() {
        boolean running = true;
        while (running) {
            int choice = view.showMainMenu();
            Runnable action = menuActions.get(choice);
            if (action != null) {
                action.run();
            } else if (choice==0) {
                running = false;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private void addPassenger() {
        command.execute(new AddPassengerCommand(passengerRepository, view));
    }

    private void addEmployee() {
        command.execute(new AddEmployeeCommand(employeeRepository, view));
    }

    private void addFlight() {
        command.execute(new AddFlightCommand(flightRepository, view));
    }

    private void listAllPassengers() {
        command.execute(new ListAllUsersCommand<>(passengerRepository));
    }

    private void listAllEmployees() {command.execute(new ListAllUsersCommand<>(employeeRepository));}

    private void listAllFlights() {
        command.execute(new ListAllFlightsCommand(flightRepository));
    }

    private void editPassenger() {
        command.execute(new EditUserCommand<>(passengerRepository, view));
    }

    private void editEmployee() {
        command.execute(new EditUserCommand<>(employeeRepository, view));
    }

    private void findPassenger() {
        command.execute(new FindUserCommand<>(passengerRepository, view));
    }

    private void findEmployee() {
        command.execute(new FindUserCommand<>(employeeRepository, view));
    }

    private void deletePassenger() {
        command.execute(new DeleteUserCommand<>(passengerRepository, view));
    }

    private void deleteEmployee() {
        command.execute(new DeleteUserCommand<>(employeeRepository, view));
    }

    private void bookFlight() {command.execute(new BookFlightCommand(passengerRepository, view, flightRepository));}
    private void undo() {
        command.undo();
    }
}

/*
 Какво подобрих?
- Динамично Map меню → лесно добавяне на нови функции
- Обработване на грешки при въвеждане
- Оптимизиран контролер без дълъг switch-case
- Чист и подреден код*/

