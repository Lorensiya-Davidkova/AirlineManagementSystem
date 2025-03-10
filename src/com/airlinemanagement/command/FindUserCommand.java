package com.airlinemanagement.command;

import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;


public class FindUserCommand<T extends User> implements Command{
    private Repository<T> repository;
    private ConsoleView view;
    public FindUserCommand(Repository<T> repo, ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public void execute() {
        int userId=view.getUserId();
        T user = repository.findById(userId);
        System.out.println(user!= null ? user: " User not found!");
    }

    @Override
    public void undo() {
        System.out.println("⚠️ Undo not applicable for finding users.");
    }
}
