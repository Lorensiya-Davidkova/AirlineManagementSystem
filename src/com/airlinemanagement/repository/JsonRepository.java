package com.airlinemanagement.repository;


import com.airlinemanagement.model.Flight;
import com.airlinemanagement.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public abstract class JsonRepository<T> {
    protected Set<T> items = new HashSet<>();
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Set<T> loadFromJson(String filePath, Class<T> clazz) {
        try (FileReader reader = new FileReader(filePath)) {
            Type setType = TypeToken.getParameterized(Set.class, clazz).getType();
            Set<T> loadedItems = gson.fromJson(reader, setType);
            //Gson избира конструктор по съвпадение на имената и типовете на параметрите с JSON ключовете.
            //Когато използвам Gson.fromJson(...), библиотеката:
            //Създава нов обект чрез наличен конструктор.
            //Зарежда стойностите директно в полетата (чрез reflection), дори и да няма setter-и.
            //Не избира конструктор автоматично според JSON – а използва безаргументен (no-arg) конструктор, или ако няма – търси съвпадение с полетата по име и тип.
            if(loadedItems.size()!=0){
                items.addAll(loadedItems);
            }

            //  И актуализацията на ID:
            int maxId = items.stream()
                    .filter(u -> u instanceof User)
                    .mapToInt(u -> ((User) u).getId())
                    .max()
                    .orElse(0);
            User.syncNextId(maxId);
            int maxFlightId = items.stream()
                    .filter(f -> f instanceof Flight)
                    .mapToInt(f -> ((Flight) f).getFlightId())
                    .max()
                    .orElse(0);
            Flight.syncNextFlightId(maxFlightId);


        } catch (IOException e) {
            System.err.println("Error loading data from " + filePath + ": " + e.getMessage());
        }
        return items;
    }



    public Set<T> getItems() {
        return items;
    }
}

