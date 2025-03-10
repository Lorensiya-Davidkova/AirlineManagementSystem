package com.airlinemanagement.command;

import com.airlinemanagement.model.Employee;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

public class AddEmployeeCommand implements Command{
    private Repository<Employee> repository;
    ConsoleView view;
    private Employee lastAdded;
    public AddEmployeeCommand(Repository<Employee> repo,ConsoleView view){
        this.repository=repo;
        this.view=view;
    }


    @Override
    public void execute() {
        Employee e=view.getEmployeeDetails();
        if (e != null) {
            repository.addUser(e);
            lastAdded = e;
        } else {
            System.out.println("Employee creation cancelled.");
        }
    }

    @Override
    public void undo() {
        if(lastAdded!=null && repository.getUsers().contains(lastAdded)){
            repository.getUsers().remove(lastAdded);
            System.out.println("⚠️ Employee addition undone.");
        }
    }

}
