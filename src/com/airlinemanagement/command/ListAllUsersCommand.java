package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;



public class ListAllUsersCommand<T extends User> implements Command {
    private Repository<T> repository;

    public ListAllUsersCommand(Repository<T> repo){
        this.repository=repo;
    }
    @Override
    public Status execute() {
      return repository.listAllUsers();
    }

/*
    @Override
    public Status undo(){return Status.warning(" Undo not applicable for listing people.");}*/
}
