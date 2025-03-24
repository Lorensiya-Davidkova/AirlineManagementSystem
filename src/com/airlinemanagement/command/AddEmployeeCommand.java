package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.Employee;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;

public class AddEmployeeCommand implements UndoableCommand{
    private UserRepository<Employee> repository;
    ConsoleView view;
    private Employee lastAdded;
    public AddEmployeeCommand(UserRepository<Employee> repo,ConsoleView view){
        this.repository=repo;
        this.view=view;
    }


    @Override
    public Status execute() {
        Employee e=view.getEmployeeDetails();
        Status status;
        if (e != null) {
            status=repository.addUser(e);
            lastAdded = e;
        } else {
           status=Status.error("Employee creation cancelled.");
        }
        return status;
    }

    @Override
    public Status undo() {
        if(lastAdded!=null && repository.getUsers().contains(lastAdded)){
            repository.getUsers().remove(lastAdded);
            return Status.warning("️Employee addition undone.");
        }
        return Status.warning("No addition for this employee!");
    }

}
