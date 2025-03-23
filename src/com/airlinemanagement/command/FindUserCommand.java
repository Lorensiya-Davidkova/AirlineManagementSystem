package com.airlinemanagement.command;


import com.airlinemanagement.Status;
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
    public Status execute() {
        Status status;
        int userId=view.getUserId();
        T user = repository.findById(userId);
        if(user!=null){
            status=Status.success(user.toString());
        }else{
            status=Status.error("User not found!");
        }
        return status;
    }


   /* @Override
    public Status undo() {
        return Status.warning("Undo not applicable for finding users.");
    }*/
}
