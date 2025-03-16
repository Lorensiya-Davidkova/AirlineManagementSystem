package com.airlinemanagement.command;


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
        if(user!=null){
            view.showSuccessMessage(user.toString());
        }else{
            view.showErrorMessage("User not found!");
        }
    }

    @Override
    public void undo() {
        view.showWarningMessage("Undo not applicable for finding users.");
    }
}
