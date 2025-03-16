package com.airlinemanagement.command;


import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

public class EditUserCommand<T extends User> implements Command{
    private Repository<T> repository;
    private ConsoleView view;
    private T previousState;

    public EditUserCommand(Repository<T> repo, ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public void execute() {
        int userId = view.getUserId();
        T user = repository.findById(userId);
        if (user != null) {
            previousState = cloneUser(user);
            view.editUser(user);
        } else {
            view.showErrorMessage("User not found!");
        }
    }
    @Override
    public void undo() {
        if (previousState != null) {
            T user = repository.findById(previousState.getId());
            if (user != null) {
                user.restoreState(previousState);
                view.showWarningMessage("Undo: Changes reverted for user " + user.getFirstName() + " " + user.getLastName());
            }
        } else {
            view.showWarningMessage("No previous state to revert to.");
        }
    }
    // Метод за клониране на потребителя
    private T cloneUser(T user) {
        return (T)user.clone();
    }
}
