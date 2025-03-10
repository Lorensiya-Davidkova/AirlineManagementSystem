package com.airlinemanagement.command;

import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;

import java.util.Set;

public class ListAllUsersCommand<T extends User> implements Command {
    private Repository<T> repository;

    public ListAllUsersCommand(Repository<T> repo){
        this.repository=repo;
    }
    @Override
    public void execute() {
        repository.listAllUsers();
    }

    @Override
    public void undo() {
        System.out.println("⚠️ Undo not applicable for listing people.");
    }
}
