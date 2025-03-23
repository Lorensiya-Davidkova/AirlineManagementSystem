package com.airlinemanagement.command;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

public class DeleteUserCommand<T extends User> implements UndoableCommand{
    private Repository<T> repository;
    private ConsoleView view;
    private T deletedUser;


    public DeleteUserCommand(Repository<T> repo,ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public Status execute() {
        int id = view.getUserId();
        deletedUser = repository.deleteUser(id);
        Status status;
        if(deletedUser==null){
           status= Status.error("No such user with ID: " + id);
        }else{
            status=Status.success("User deleted:" + deletedUser.getFirstName() +" "+ deletedUser.getLastName());
        }
        return status;
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
}
