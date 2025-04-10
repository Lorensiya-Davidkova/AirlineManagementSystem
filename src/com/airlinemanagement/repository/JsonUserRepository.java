package com.airlinemanagement.repository;

import com.airlinemanagement.Status;
import com.airlinemanagement.StatusType;
import com.airlinemanagement.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonUserRepository<T extends User> implements UserRepository<T> {
   private Set<T> users;
    private final String filePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Class<T> clazz;

    public JsonUserRepository(String filePath, Class<T> clazz) throws IOException{
        this.filePath = filePath;
        this.clazz = clazz;
        this.users= Collections.synchronizedSet(new HashSet<>());
        load();
    }

    @Override
    public Status addUser(T user) {
        synchronized (users) {
            if (users.add(user)) {
                return save();
            }
            return Status.warning("This user already exists!");
        }
    }

    @Override
    public Result<T> deleteUser(int id) {
        synchronized (users) {
            T user = findById(id);
            if (user != null) {
                users.remove(user);
                Status saveStatus = save();
                if (saveStatus.getType() == StatusType.ERROR) {
                    return Result.warning(user, saveStatus.getMessage());
                }
                return Result.success(user, "User deleted successfully.");
            } else {
                return Result.error("User not found with ID: " + id);
            }
        }
    }
    @Override
    public T findById(int id) {
        synchronized (users) {
            return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        }
    }

    public Result<Set<T>> listAllUsers() {
        synchronized (users) {
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
    public Status updateUser(T user) {
        save();
        return Status.success("User updated successfully.");
    }


    private void load() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            Type setType = TypeToken.getParameterized(Set.class, clazz).getType();
            Set<T> loaded = gson.fromJson(reader, setType);
            if (loaded != null) {
                synchronized (users) {
                    users.addAll(loaded);
                    syncIds(loaded);
                }
            }
        }
    }


    public Status save() {
        synchronized (users) {
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(users, writer);
                return Status.success("User added successfully.");
            } catch (IOException e) {
                return Status.error("Error saving user in file: " + e.getMessage());
            }
        }
    }

    private void syncIds(Set<T> loaded) {
        int maxId = loaded.stream().mapToInt(User::getId).max().orElse(0);
        User.syncNextId(maxId);
    }
}

