package com.airlinemanagement.repository;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;

import java.util.Set;

public class InMemoryUserRepository<T extends User> implements UserRepository<T> {
    private Set<T> users;
    public InMemoryUserRepository(Set<T> users){
        this.users=users;
    }
    @Override
    public synchronized Status addUser(T user) {
        if (users.add(user)) {
            return Status.success("Successfully added user!");
        } else {
            return Status.warning("This user already exists!");
        }
    }

    @Override
    public synchronized T deleteUser(int id) {
        T user = findById(id);
        if (user != null) {
            users.remove(user);
            return user; // Връщаме изтрития обект, за да го запазим за `undo()`
        } else {
            return null;
        }
    }

    @Override
    public T findById(int id) {
        for (T user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Status listAllUsers() {
        if (users.isEmpty()) {
            return Status.warning("No users in the repository!");
        } else {
            return Status.success(users.toString());
        }
    }

    @Override
    public Set<T> getUsers() {
        return users;
    }
}
