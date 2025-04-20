package com.airlinemanagement.controller;

import com.airlinemanagement.FilePaths;
import com.airlinemanagement.Status;
import com.airlinemanagement.command.*;
import com.airlinemanagement.model.Employee;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.*;
import com.airlinemanagement.view.ConsoleView;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class AirlineController {
    private UserRepository<Passenger> passengerRepository;
    private UserRepository<Employee> employeeRepository;
    private FlightRepository flightRepository;
    private final ConsoleView view;
    private final CommandManager commandManager;
    private final Map<Integer, Supplier<Command>> menuActions = new LinkedHashMap<>();

    public AirlineController() {
        this.view = new ConsoleView();
        this.commandManager = new CommandManager();
    }

    private void initializeMenu() {
        menuActions.put(1, () -> new AddPassengerCommand(passengerRepository, view));
        menuActions.put(2, () -> new ListAllUsersCommand<>(passengerRepository, "│ 2. 📋List all passengers                    │"));
        menuActions.put(3, () -> new EditUserCommand<>(passengerRepository, view, "│ 3. ✏️  Edit passenger                       │"));
        menuActions.put(4, () -> new FindUserCommand<>(passengerRepository, view, "│ 4. 🔍  Find passenger                       │"));
        menuActions.put(5, () -> new DeleteUserCommand<>(passengerRepository, view, "│ 5. ❌  Delete passenger                     │",flightRepository));

        menuActions.put(6, () -> new AddEmployeeCommand(employeeRepository, view));
        menuActions.put(7, () -> new ListAllUsersCommand<>(employeeRepository, "│ 7.  📋  List all employees                  │"));
        menuActions.put(8, () -> new EditUserCommand<>(employeeRepository, view, "│ 8. ✏️  Edit employee                        │"));
        menuActions.put(9, () -> new FindUserCommand<>(employeeRepository, view, "│ 9.🔍  Find employee                         │" ));
        menuActions.put(10, () -> new DeleteUserCommand<>(employeeRepository, view, "│ 10. ❌  Delete employee                     │",flightRepository));

        menuActions.put(11, () -> new AddFlightCommand(flightRepository, view));
        menuActions.put(12, () -> new ListAllFlightsCommand(flightRepository));
        menuActions.put(13, () -> new BookFlightCommand(passengerRepository, view, flightRepository));

        menuActions.put(14, () -> new UndoCommand(commandManager));
    }

    public void start() {
        chooseRepositoryType();
        initializeMenu();

        boolean running = true;
        while (running) {
            List<Command> commandList = menuActions.values().stream().map(Supplier::get).toList();
            int choice = view.showMainMenuAndGetChoice(commandList);

            if (choice == 0) {
                running = false;
            /*} else if (choice == 14) {
                executeUndo();*/
            } else {
                Supplier<Command> supplier = menuActions.get(choice);
                if (supplier != null) {
                    Command command = supplier.get();
                    executeCommand(command);
                } else {
                    view.printInvalidChoice();
                }
            }
        }
    }

    private void executeCommand(Command command) {
        Status status = commandManager.execute(command);
        printStatus(status);
    }

    /*private void executeUndo() {
        Status status = commandManager.undo();
        printStatus(status);
    }*/

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
        boolean loadedFromJson = false;

        if (choice == 2) {
            try {
                passengerRepository = new JsonUserRepository<>(FilePaths.PASSENGERS, Passenger.class);
                employeeRepository = new JsonUserRepository<>(FilePaths.EMPLOYEES, Employee.class);
                flightRepository = new JsonFlightRepository(FilePaths.FLIGHTS);
                loadedFromJson = true;
            } catch (IOException e) {
                view.showErrorMessage("Can not load repositories from JSON. Switching to in-memory repositories.");
            }
        }

        if (!loadedFromJson) {
            passengerRepository = new InMemoryUserRepository<>();
            employeeRepository = new InMemoryUserRepository<>();
            flightRepository = new InMemoryFlightRepository();
            view.showSuccessMessage("Starting with empty in-memory repositories.");
        } else {
            view.showSuccessMessage("Repositories loaded successfully from JSON.");
        }
    }
}
/*добавих допълнителен клас за undoCommand, защото да изглежда по-прегледно в start() метода по-горе
да няма отделна проверка за 14 и съответно да се премахне еxecuteUndo(); методът*/





