package com.airlinemanagement.view;

import com.airlinemanagement.model.*;
import com.airlinemanagement.repository.Repository;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMainMenu(String undoLabel) {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│               ✈️  MAIN MENU                 │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ 1. 👤  Add new passenger                    │");
        System.out.println("│ 2. 📋  List all passengers                  │");
        System.out.println("│ 3. ✏️  Edit passenger                       │");
        System.out.println("│ 4. 🔍  Find passenger                       │");
        System.out.println("│ 5. ❌  Delete passenger                     │");
        System.out.println("│ 6. 👥  Add new employee                     │");
        System.out.println("│ 7. 📋  List all employees                   │");
        System.out.println("│ 8. ✏️  Edit employee                        │");
        System.out.println("│ 9. 🔍  Find employee                        │");
        System.out.println("│10. ❌  Delete employee                      │");
        System.out.println("│11. ✈️  Add new flight                       │");
        System.out.println("│12. 📋  List all flights                     │");
        System.out.println("│13. ➕✈️  Book a flight                      │");
        System.out.println("|14. ↩️  " + undoLabel + "                    ");
        System.out.println("│ 0. 🚪  Exit                                 │");
        System.out.println("└─────────────────────────────────────────────┘");
        System.out.print("✏️  Please enter your choice: ");

        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            showErrorMessage("Invalid input. Please enter a number.");
            return -1;
        }

    }
    public Passenger getPassengerDetails() {
        System.out.print("First Name: ");
        String firstName = scanner.next();
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        System.out.print("Phone: ");
        String phone = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        return new Passenger(firstName, lastName, phone, email);
    }

    public Employee getEmployeeDetails() {
        System.out.print("First Name: ");
        String firstName = scanner.next();
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        System.out.print("Phone: ");
        String phone = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();

        double salary = 0;
        while (true) {
            System.out.print("Salary: ");
            try {
                salary = scanner.nextDouble();
                if (salary <= 0) {
                    throw new IllegalArgumentException("Salary must be a positive number.");
                }
                break;
            } catch (InputMismatchException e) {
                showErrorMessage("Invalid salary input. Please enter a numeric value.");
                scanner.next(); // Изчиства грешния вход
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        Position position = null;
        while (position == null) {
            System.out.print("Position (PILOT, CO_PILOT, FLIGHT_ATTENDANT, MANAGER, OTHER): ");
            String positionInput = scanner.next().toUpperCase();
            try {
                position = Position.valueOf(positionInput);
            } catch (IllegalArgumentException e) {
                showErrorMessage("Invalid position. Please enter a valid position from the list.");
            }
        }

        return new Employee(firstName, lastName, phone, email, salary, position);
    }


    public Flight getFlightDetails() {
        System.out.print("Flight Number: ");
        String flightNumber = scanner.next();
        System.out.print("Departure: ");
        String departure = scanner.next();
        System.out.print("Destination: ");
        String destination = scanner.next();
        System.out.print("Departure Time: ");
        String departureTime = scanner.next();
        return new Flight(flightNumber, departure, destination, departureTime);
    }

    public  int getUserId() {
        int id;
        while (true) {
            System.out.print("Enter ID: ");
            try {
                id = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                showErrorMessage("Invalid ID. Please enter a numeric value.");
                scanner.next(); // Изчиства грешния вход
            }
        }
        return id;
    }

    public  String getFlightNumber(){
        System.out.print("Enter flight number: ");
        String number=scanner.nextLine();
        return number;
    }
    public void editUser(User user) {
        System.out.println("Editing user: " + user.getFirstName() + " " + user.getLastName());

        System.out.print("Enter new first name (or press Enter to keep current: " + user.getFirstName() + "): ");
       // scanner.nextLine(); // Изчиства буфера
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            user.setFirstName(firstName);
        }

        System.out.print("Enter new last name (or press Enter to keep current: " + user.getLastName() + "): ");
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            user.setLastName(lastName);
        }

        System.out.print("Enter new phone number (or press Enter to keep current: " + user.getTelephoneNumber() + "): ");
        String phoneNumber = scanner.nextLine().trim();
        if (!phoneNumber.isEmpty()) {
            user.setTelephoneNumber(phoneNumber);
        }

        System.out.print("Enter new email (or press Enter to keep current: " + user.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        if (user instanceof Employee) {
            Employee employee = (Employee) user;

            System.out.print("Enter new salary (or press Enter to keep current: $" + employee.getSalary() + "): ");
            String salaryInput = scanner.nextLine().trim();
            if (!salaryInput.isEmpty()) {
                try {
                    double salary = Double.parseDouble(salaryInput);
                    employee.setSalary(salary);
                } catch (NumberFormatException e) {
                    showErrorMessage("Invalid salary input. Salary not updated.");
                }
            }

            System.out.print("Enter new position (PILOT, CO_PILOT, FLIGHT_ATTENDANT, MANAGER, OTHER) or press Enter to keep current: " + employee.getPosition() + "): ");
            String positionInput = scanner.nextLine().trim();
            if (!positionInput.isEmpty()) {
                try {
                    Position position = Position.valueOf(positionInput.toUpperCase());
                    employee.setPosition(position);
                } catch (IllegalArgumentException e) {
                    showErrorMessage("Invalid position input. Position not updated.");
                }
            }
        }

        showSuccessMessage("User successfully updated!");
    }

    public void printInvalidChoice(){
        showErrorMessage("Invalid option. Please try again.");
    }

        public void showSuccessMessage(String message) {
            System.out.println("✅ " + message);
        }
        public void showErrorMessage(String message) {
            System.out.println("❌ " + message);
        }

        public void showWarningMessage(String message) {
            System.out.println("⚠️ " + message);
        }
        public void printAllItems(Set items){
            items.forEach(System.out::println);
        }

}


