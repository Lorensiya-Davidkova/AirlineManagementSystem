package com.airlinemanagement.model;

import java.util.Objects;


public abstract class User{
    private static int nextId =1;
    private int id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;

    public User(String firstName, String lastName, String telephoneNumber, String email) {
        this.id = nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }
    public User(int id, String firstName, String lastName, String telephoneNumber, String email) {
        this.id = id; // Задаваме ID ръчно, без да инкрементираме nextId
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;

    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + firstName + " " + lastName + ", Phone: " + telephoneNumber + ", Email: " + email;
    }
    @Override
    public abstract User clone(); // Декларирам `clone()` като абстрактен метод, за да не се полуава да питам instance of при колониране в edit-а
    public void restoreState(User previousState) {
        this.firstName = previousState.firstName;
        this.lastName = previousState.lastName;
        this.telephoneNumber = previousState.telephoneNumber;
        this.email = previousState.email;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return this.getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static void syncNextId(int maxExistingId) {
        if (maxExistingId >= nextId) {
            nextId = maxExistingId + 1;
        }
    }
}
