package com.airlinemanagement.repository;

import com.airlinemanagement.model.User;

import java.util.HashSet;
import java.util.Set;

public class Repository<T extends User> {
    private Set<T> users = new HashSet<>();

    public synchronized void addUser(T user) {
        if (users.add(user)) {
            System.out.println("🎉 Successfully added: " + user);
        } else {
            System.out.println("User already exists!");
        }
    }

    public Set<T> getUsers() {
        return users;
    }

    public T findById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public synchronized T deleteUser(int id) {
        T user = findById(id);
        if (user != null) {
            users.remove(user);
            System.out.println("✅ User deleted: " + user.getFirstName() + " " + user.getLastName());
            return user; // Връщаме изтрития обект, за да го запазим за `undo()`
        } else {
            System.out.println("❌ No such user with ID: " + id);
            return null;
        }
    }


    public void listAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users available!");
        } else {
            users.forEach(System.out::println);
        }
    }
}
/*
- users вече е Set, което не позволява дублирани обекти.
- Добавен е findById() за лесно търсене.
- Методът deleteUser() премахва потребител по ID*/