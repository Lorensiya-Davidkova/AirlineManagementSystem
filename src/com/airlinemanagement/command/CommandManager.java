package com.airlinemanagement.command;

import com.airlinemanagement.Status;
import com.airlinemanagement.view.ConsoleView;

import java.util.Stack;

public class CommandManager {
    private Stack<UndoableCommand> undoStack=new Stack<>();

    public Status execute(Command command) {
        Status status=command.execute();
        if(command instanceof UndoableCommand) {
            this.undoStack.push((UndoableCommand) command);
        }
        return status;
    }
    public Status undo(){
        Status status;
        if(!undoStack.isEmpty()){
            UndoableCommand command=undoStack.pop();
            status=command.undo();
        }else{
            status=Status.warning("Nothing to undo!");
        }
        return status;
    }
    public String getLastCommandName() {
        if (!undoStack.isEmpty()) {
            return undoStack.peek().getClass().getSimpleName();
        }
        return "Undo (No action)";
    }
}
/*
* public class CommandManager {
    private Stack<Command> undoStack = new Stack();
    private Stack<Command> redoStack = new Stack();
    private ConsoleView view = new ConsoleView();

    public CommandManager() {
    }

    public void execute(Command command) {
        command.execute();
        this.undoStack.push(command);
        this.redoStack.clear();
    }

    public void undo() {
        if (!this.undoStack.isEmpty()) {
            Command command = (Command)this.undoStack.pop();
            command.undo();
            this.redoStack.push(command);
        } else {
            this.view.showWarningMessage("Nothing to undo!");
        }

    }

    public String getLastCommandName() {
        return !this.undoStack.isEmpty() ? "Undo " + ((Command)this.undoStack.peek()).getClass().getSimpleName() : "Undo (No action)";
    }
}*/
