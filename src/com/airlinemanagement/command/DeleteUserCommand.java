package com.airlinemanagement.command;

import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

public class DeleteUserCommand<T extends User> implements Command{
    private Repository<T> repository;
    private ConsoleView view;
    private T deletedUser;


    public DeleteUserCommand(Repository<T> repo,ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public void execute() {
        int id = view.getUserId();
        deletedUser = repository.deleteUser(id);
    }

    @Override
    public void undo() {
        if (deletedUser == null) {
            System.out.println("⚠️ No user deletion to undo.");
            return;
        }

        repository.getUsers().add(deletedUser);
        System.out.println("↩️ Undo: User restored - " + deletedUser.getFirstName() + " " + deletedUser.getLastName());

        deletedUser = null; // Изчистваме за следващо `undo()`
    }
}
