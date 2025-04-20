package com.airlinemanagement.repository;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.Flight;
import com.airlinemanagement.model.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryUserRepository<T extends User> implements UserRepository<T> {
    private Set<T> users;
    public InMemoryUserRepository(){
        this.users = Collections.synchronizedSet(new HashSet<>());
    }
    @Override
    public  Status addUser(T user) {
        synchronized (users) {
            if (users.add(user)) {
                return Status.success("Successfully added user!");
            } else {
                return Status.warning("This user already exists!");
            }
        }
    }

    @Override
    public Result<T> deleteUser(int id) {
        synchronized(users) {
            T user = findById(id);
            if (user != null) {
                user.onDelete();//кенсалираме полетите на пътника
                users.remove(user);
                return Result.success(user, "User deleted:" + user.getFirstName() + " " + user.getLastName());
            } else {
                return Result.error("No such user with ID: " + id);
            }
        }
    }


    @Override
    public T findById(int id) {
        synchronized (users) {
            for (T user : users) {
                if (user.getId() == id) {
                    return user;
                }
            }
            return null;
        }
    }

    public Result<Set<T>> listAllUsers() {
        synchronized(users) {
            if (users.isEmpty()) {
                return Result.warning(new HashSet<>(), "No users in the repository.");
            }
            return Result.success(new HashSet<>(users), "Users listed successfully.");
        }
    }

    @Override
    public Set<T> getUsers() {
        return users;
    }

    @Override
    public void persist() {}

    @Override
    public void updateFlights(T user) {
        if(user.getFlights()!=null) {
            for (Flight flight : user.getFlights()) {
                flight.bookSeat();
            }
        }
    }


}
