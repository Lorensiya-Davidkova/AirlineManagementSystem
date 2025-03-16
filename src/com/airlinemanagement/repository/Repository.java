package com.airlinemanagement.repository;
import com.airlinemanagement.model.User;
import com.airlinemanagement.view.ConsoleView;

import java.util.HashSet;
import java.util.Set;

public class Repository<T extends User>{
    private Set<T> users = new HashSet<>();
    private ConsoleView view=new ConsoleView();
    private int nextId=1;

    public synchronized void addUser(T user) {
        if (users.add(user)) {
           view.showSuccessMessage("Successfully added user!");
        } else {
            view.showWarningMessage("This user already exists!");
        }
    }

    public Set<T> getUsers() {
        return users;
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
        T user =findById(id);
        if (user != null) {
            users.remove(user);
            view.showSuccessMessage("User deleted: " + user.getFirstName() + " " + user.getLastName());
            return user; // Връщаме изтрития обект, за да го запазим за `undo()`
        } else {
            view.showErrorMessage("No such user with ID: " + id);
            return null;
        }
    }



    public void listAllUsers() {
        if (users.isEmpty()) {
            view.showWarningMessage("No users in the repository!");
        } else {
            view.printAllItems(users);
        }
    }

}
/*
- users вече е Set, което не позволява дублирани обекти.
- Добавен е findById() за лесно търсене.
- Методът deleteUser() премахва потребител по ID*/