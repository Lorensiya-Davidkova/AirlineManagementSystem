package com.airlinemanagement.model;

import java.util.Objects;

public class Employee extends User {
    private double salary;
    private Position position;

    public Employee(String firstName, String lastName, String telephoneNumber, String email, double salary, Position position) {
        super(firstName, lastName, telephoneNumber, email);
        this.setSalary(salary);
        this.setPosition(position);
    }

    public Employee(int id,String firstName, String lastName, String telephoneNumber, String email, double salary, Position position) {
        super(id,firstName, lastName, telephoneNumber, email);
        this.salary = salary;
        this.position = position;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Invalid position. Must be one of: PILOT, CO_PILOT, FLIGHT_ATTENDANT, MANAGER, OTHER.");
        }
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }


    public Position getPosition() {
        return position;
    }



    @Override
    public String toString() {
        return super.toString() + ", Position: " + position + ", Salary: $" + salary;
    }

    @Override
    public User clone() {
        return new Employee(getId(), getFirstName(), getLastName(), getTelephoneNumber(), getEmail(), salary, position);
    }
    public void restoreState(User previousState) {
        super.restoreState(previousState);
        if(previousState instanceof Employee prevEmployee){
            this.salary = prevEmployee.salary;
            this.position = prevEmployee.position;
        }
    }
}
/*
Какво е променено?
- Конструкторът не използва сетъри.
- Параметърът position е Position вместо String, за да избегнем грешки.
 */
