package com.airlinemanagement.repository;

import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

public class JsonUserRepository<T extends User> implements UserRepository<T> {
   private Set<T> users;
    private final String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Class<T> clazz;

    public JsonUserRepository(String filePath, Class<T> clazz,Set<T> users) {
        this.filePath = filePath;
        this.clazz = clazz;
        this.users=users;
        load();
    }

    @Override
    public Status addUser(T user) {
        if (users.add(user)) {
            save();
            return Status.success("Successfully added user!");
        }
        return Status.warning("This user already exists!");
    }

    @Override
    public T deleteUser(int id) {
        T found = findById(id);
        if (found != null) {
            users.remove(found);
            save();
        }
        return found;
    }

    @Override
    public T findById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
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

    private void load() {
        try (FileReader reader = new FileReader(filePath)) {
            Type setType = TypeToken.getParameterized(Set.class, clazz).getType();
            Set<T> loaded = gson.fromJson(reader, setType);
            if (loaded != null) {
                users.addAll(loaded);
                syncIds();
            }
        } catch (IOException e) {
            System.err.println("Load error: " + e.getMessage());
        }
    }

    private void save() {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    private void syncIds() {
        int maxId = users.stream().mapToInt(User::getId).max().orElse(0);
        User.syncNextId(maxId);
    }
}

