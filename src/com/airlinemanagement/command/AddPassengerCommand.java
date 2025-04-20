package com.airlinemanagement.command;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;

public class AddPassengerCommand implements UndoableCommand{
    private UserRepository<Passenger> repo;
    private ConsoleView consoleView;
    private Passenger addedPassenger;
    public AddPassengerCommand(UserRepository<Passenger> r, ConsoleView view){
        this.repo=r;
        this.consoleView=view;
    }
    @Override
    public Status execute() {
        Passenger passenger = consoleView.getPassengerDetails();

        Status status;
        if (passenger != null) {
            status=repo.addUser(passenger);
            addedPassenger = passenger;
        } else {
           status=Status.error("Passenger creation cancelled.");
        }
        return status;
    }

    @Override
    public String getDisplayText() {
        return ("│ 1. 👤  Add new passenger                    │");
        //return (" 👤  Add new passenger");
    }

    @Override
    public Status undo() {
        if (addedPassenger != null) {
            boolean removed = repo.getUsers().remove(addedPassenger);
           // repo.deleteUser(addedPassenger.getId());
            if (removed) {
                repo.persist();
                return Status.success("Passenger addition undone: " + addedPassenger.getFirstName() + " " + addedPassenger.getLastName());
            } else {
                return Status.warning("Passenger not found.");
            }
        }
        return Status.error("No addition for this passenger supported!");
    }

    @Override
    public String getUndoDisplayText() {
        return "Undo add passenger: "+addedPassenger.getFirstName();
    }
}
