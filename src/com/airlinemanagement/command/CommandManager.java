package com.airlinemanagement.command;

import com.airlinemanagement.view.ConsoleView;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack=new Stack<>();
    private Stack<Command> redoStack=new Stack<>();
    private ConsoleView view=new ConsoleView();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }
    public void undo(){
        if(!undoStack.isEmpty()){
            Command command=undoStack.pop();
            command.undo();
            redoStack.push(command);
        }else{
            view.showWarningMessage("Nothing to undo!");
        }
    }
    public String getLastCommandName() {
        if (!undoStack.isEmpty()) {
            return "Undo " + undoStack.peek().getClass().getSimpleName();
        }
        return "Undo (No action)";
    }
}
