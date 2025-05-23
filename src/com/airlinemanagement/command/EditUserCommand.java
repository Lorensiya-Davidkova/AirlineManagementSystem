package com.airlinemanagement.command;


import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;

public class EditUserCommand<T extends User> implements UndoableCommand{
    private UserRepository<T> repository;
    private ConsoleView view;
    private T previousState;
    private final String displayText;

    public EditUserCommand(UserRepository<T> repo, ConsoleView view,String displayText){
        this.repository=repo;
        this.view=view;
        this.displayText=displayText;
    }
    @Override
    public Status execute() {
        Status status;
        int userId = view.getUserId();
        T user = repository.findById(userId);
        if (user != null) {
            previousState = cloneUser(user);
            view.editUser(user);
            repository.persist();
            status=Status.success("User successfully updated!");
        } else {
            status=Status.error("User not found!");
        }
        return status;
    }

    @Override
    public String getDisplayText() {
        return displayText;
    }

    @Override
    public Status undo() {
        if (previousState != null) {
            T user = repository.findById(previousState.getId());
            if (user != null) {
                user.restoreState(previousState);
                repository.persist();
                return Status.warning("Undo: Changes reverted for user " + user.getFirstName() + " " + user.getLastName());
            }
        }
        return Status.warning("No previous state to revert to.");
    }

    @Override
    public String getUndoDisplayText() {
        return "Undo edit user";
    }

    // Метод за клониране на потребителя
    private T cloneUser(T user) {
        return (T)user.clone();
    }
}
