package com.airlinemanagement.command;
import com.airlinemanagement.Status;
import com.airlinemanagement.model.User;
import com.airlinemanagement.repository.FlightRepository;
import com.airlinemanagement.repository.Result;
import com.airlinemanagement.repository.UserRepository;
import com.airlinemanagement.view.ConsoleView;

public class DeleteUserCommand<T extends User> implements UndoableCommand{
    private UserRepository<T> repository;
    private ConsoleView view;
    private T deletedUser;
    private final String displayText;
    private FlightRepository flightRepository;

    public DeleteUserCommand(UserRepository<T> repo, ConsoleView view, String displayText, FlightRepository flightRepository){
        this.repository=repo;
        this.view=view;
        this.displayText=displayText;
        this.flightRepository=flightRepository;
    }
    @Override
    public Status execute() {
        int id = view.getUserId();
        Result<T> result = repository.deleteUser(id);
        deletedUser = result.getData();
        /*този метод е само ако използваме файл, защото ако не го използваме си викаме в repository.deleteUser(id)
        който освобождава местата*/
        flightRepository.persist();
        return result.getStatus();
    }

    @Override
    public String getDisplayText() {
        return displayText;
    }

    @Override
    public Status undo() {
        Status status;
        if (deletedUser == null) {
            return Status.warning("No user deletion to undo.");
        }

        repository.getUsers().add(deletedUser);
        /*тук трябва да помисля, какво ще прави програмата със самите полети, когато даден пътник X си отмени изтриването
        на профила
        - ако даден изтриване на профил и е бил резервирал билет, който е имал 1 свободно място=>
        това свободно място ще се освободи и пътник Y например може да го резервира
        За да се дотигне до undo на delete, задължително се минава през undo на book-ването =>
        място си се освобождава (наетото от пътник Y) и се възвръща към стария си притежател (пътник X)*/
        repository.updateFlights(deletedUser);
        repository.persist();
        flightRepository.persist();
        status= Status.success("Undo: User restored - " + deletedUser.getFirstName() + " " + deletedUser.getLastName());
        deletedUser = null; // Изчистваме за следващо `undo()`
        return status;
    }

    @Override
    public String getUndoDisplayText() {
        if(deletedUser==null){
            return "Undo delete user.";
        }
        return "Undo delete "+deletedUser.getClass().getSimpleName()+" : "+ deletedUser.getFirstName();
    }
}
