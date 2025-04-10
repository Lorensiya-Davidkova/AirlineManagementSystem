package com.airlinemanagement.repository;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;


import java.util.Set;

public interface UserRepository<T extends User> {
    Status addUser(T user);
    Result<T> deleteUser(int id);
    T findById(int id);
    Result<Set<T>> listAllUsers();
    Set<T> getUsers();

    Status updateUser(T user);
}
