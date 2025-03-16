package com.airlinemanagement.command;

import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;


public class ListAllUsersCommand<T extends User> implements Command {
    private Repository<T> repository;
    private ConsoleView view;
    public ListAllUsersCommand(Repository<T> repo, ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public void execute() {
        repository.listAllUsers();
    }

    @Override
    public void undo() {
        view.showWarningMessage(" Undo not applicable for listing people.");
    }
}
