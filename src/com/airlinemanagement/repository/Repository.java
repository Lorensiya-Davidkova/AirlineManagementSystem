package com.airlinemanagement.repository;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;

import java.util.HashSet;
import java.util.Set;

public class Repository<T extends User> extends JsonRepository<T> {
    private Set<T> users = new HashSet<>();

 
    public synchronized Status addUser(T user) {
        if (users.add(user)) {
            return Status.success("Successfully added user!");
        } else {
            return Status.warning("This user already exists!");
        }
    }

    public Set<T> getUsers() {
        return users;
    }
    public void setUsers(Set<T> users){
        this.users=users;
    }

    public T findById(int id) {
        for (T user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public synchronized T deleteUser(int id) {
        T user = findById(id);
        if (user != null) {
            users.remove(user);
            return user; // Връщаме изтрития обект, за да го запазим за `undo()`
        } else {
            return null;
        }
    }

    public Status listAllUsers() {
        if (users.isEmpty()) {
            return Status.warning("No users in the repository!");
        } else {
            return Status.success(users.toString());
        }
    }
}
