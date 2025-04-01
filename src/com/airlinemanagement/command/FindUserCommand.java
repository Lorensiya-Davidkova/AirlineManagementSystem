package com.airlinemanagement.command;


import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;


public class FindUserCommand<T extends User> implements Command{
    private UserRepository<T> repository;
    private ConsoleView view;
    private final String displayText;
    public FindUserCommand(UserRepository<T> repo, ConsoleView view,String displayText){
        this.repository=repo;
        this.view=view;
        this.displayText=displayText;
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

    @Override
    public String getDisplayText() {
        //("│ 4. 🔍  Find passenger                       │\n│ 9. 🔍  Find employee                        │");
        return displayText;
    }
}
