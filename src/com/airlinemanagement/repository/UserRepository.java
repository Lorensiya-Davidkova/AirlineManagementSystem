package com.airlinemanagement.repository;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;

import java.util.Set;

public interface UserRepository<T extends User> {
    Status addUser(T user);
    T deleteUser(int id);
    T findById(int id);
    Status listAllUsers();
    Set<T> getUsers();
}

