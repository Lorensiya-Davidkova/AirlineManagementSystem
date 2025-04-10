package com.airlinemanagement.command;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Result;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;

public class DeleteUserCommand<T extends User> implements UndoableCommand{
    private UserRepository<T> repository;
    private ConsoleView view;
    private T deletedUser;
    private final String displayText;

    public DeleteUserCommand(UserRepository<T> repo,ConsoleView view,String displayText){
        this.repository=repo;
        this.view=view;
        this.displayText=displayText;
    }
    @Override
    public Status execute() {
        int id = view.getUserId();
        Result<T> result = repository.deleteUser(id);
        deletedUser = result.getData();
        return result.getStatus();
    }

    @Override
    public String getDisplayText() {
        return displayText;
    }

    @Override
    public Status undo() {
        Status status;
        if (deletedUser == null) {
            return Status.warning("No user deletion to undo.");
        }

        repository.getUsers().add(deletedUser);
        status= Status.success("Undo: User restored - " + deletedUser.getFirstName() + " " + deletedUser.getLastName());
        deletedUser = null; // Изчистваме за следващо `undo()`
        return status;
    }

    @Override
    public String getUndoDisplayText() {
        if(deletedUser==null){
            return "Undo delete user.";
        }
        return "Undo delete "+deletedUser.getClass().getSimpleName()+" : "+ deletedUser.getFirstName();
    }
}
