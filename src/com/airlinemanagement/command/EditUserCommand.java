package com.airlinemanagement.command;

import com.airlinemanagement.model.Employee;
import com.airlinemanagement.model.Passenger;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.Repository;
import com.airlinemanagement.view.ConsoleView;

public class EditUserCommand<T extends User> implements Command{
    private Repository<T> repository;
    private ConsoleView view;
    private T previousState;

    public EditUserCommand(Repository<T> repo,ConsoleView view){
        this.repository=repo;
        this.view=view;
    }
    @Override
    public void execute() {
        int userId = view.getUserId();
        T user = repository.findById(userId);
        if (user != null) {
            previousState = cloneUser(user);
            view.editUser(user);
        } else {
            System.out.println("❌ User not found!");
        }
    }

    @Override
    public void undo() {
        if (previousState != null) {
            T user = repository.findById(previousState.getId());
            if (user != null) {
                // Възстанови предишните стойности
                user.setFirstName(previousState.getFirstName());
                user.setLastName(previousState.getLastName());
                user.setTelephoneNumber(previousState.getTelephoneNumber());
                user.setEmail(previousState.getEmail());

                if (user instanceof Employee) {
                    ((Employee) user).setSalary(((Employee) previousState).getSalary());
                    ((Employee)user).setPosition(((Employee)previousState).getPosition().toString());
                }

                System.out.println("↩️ Undo: Changes reverted for user " + user.getFirstName() + " " + user.getLastName());
            }
        } else {
            System.out.println("⚠️ No previous state to revert to.");
        }
    }
    // Метод за клониране на потребителя
    private T cloneUser(T user) {
        return (T)user.clone();
    }
    /*
    * Всеки клас в Java наследява метода clone() от Object.
    *  Този метод създава копие на обекта в паметта, но по подразбиране прави плитко копие (shallow copy).
    * Проблеми при използване на clone() в моя случай:
    * Трябва да имплементирам Cloneable интерфейса, иначе clone() ще хвърли CloneNotSupportedException.
    * Прави само плитко копие (shallow copy). Ако има обекти като List, Set, или Map, те няма да бъдат копирани, а само техните референции.
    * По-малко контрол – ако някой забрави да овъррайдне clone(), може да се получи неочаквано поведение.*/
}
