package com.airlinemanagement.command;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

public class AddPassengerCommand implements Command{
    private Repository<Passenger> repo;
    private ConsoleView consoleView;
    private Passenger addedPassenger;
    public AddPassengerCommand(Repository<Passenger> r, ConsoleView view){
        this.repo=r;
        this.consoleView=view;
    }
    @Override
    public void execute() {
        Passenger passenger = consoleView.getPassengerDetails();
        if (passenger != null) {
            repo.addUser(passenger);
            addedPassenger = passenger;
        } else {
           consoleView.showErrorMessage("Passenger creation cancelled.");
        }
    }

    @Override
    public void undo() {
        if (addedPassenger != null) {
            boolean removed = repo.getUsers().remove(addedPassenger);
            if (removed) {
                consoleView.showSuccessMessage("Passenger addition undone: " + addedPassenger.getFirstName() + " " + addedPassenger.getLastName());
            } else {
                consoleView.showWarningMessage("Passenger not found.");
            }
        }
    }
}
