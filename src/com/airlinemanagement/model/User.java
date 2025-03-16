package com.airlinemanagement.model;
public abstract class User {
    private static int nextId = 1;
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
    public abstract User clone(); // Декларираме `clone()` като абстрактен метод, за да не се полуава да питам instance of при колониране в edit-а
    public void restoreState(User previousState) {
        this.firstName = previousState.firstName;
        this.lastName = previousState.lastName;
        this.telephoneNumber = previousState.telephoneNumber;
        this.email = previousState.email;
    }

}
/*
Какво е променено?
- Направих класа абстрактен, защото User няма да бъде създаван директно, а само чрез Passenger и Employee.
- Конструкторът вече не използва сетъри, а директно инициализира полетата.
- id е final и се сетва само веднъж.
- Сменено е автоматичното ID генериране – сега се случва в User, а не в Passenger и Employee.
- Вече има абстрактен метод clone()
 */
