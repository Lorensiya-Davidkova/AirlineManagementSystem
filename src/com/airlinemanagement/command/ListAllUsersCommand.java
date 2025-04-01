package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Result;
import com.airlinemanagement.repository.UserRepository;

import java.util.Set;


public class ListAllUsersCommand<T extends User> implements Command {
    private UserRepository<T> repository;

    private final String displayText;
    public ListAllUsersCommand(UserRepository<T> repo,String displayText) {
        this.repository = repo;
        this.displayText=displayText;
    }

    @Override
    public Status execute() {
        Result<Set<T>> result = repository.listAllUsers();
        Set<T> users = result.getData(); //users= new HashSet<>() || users=new HashSet<>(users)
        Status status=result.getStatus();//status= "No users in the repository."); || ("Users listed successfully.");
        if (users.isEmpty()) {
            return status;
        }

        StringBuilder sb = new StringBuilder("📋 Users:\n");
        for (T user : users) {
            sb.append(" • ").append(user).append("\n");
        }
        return Status.success(sb.toString());
    }

    @Override
    public String getDisplayText() {
        return displayText;
    }
}