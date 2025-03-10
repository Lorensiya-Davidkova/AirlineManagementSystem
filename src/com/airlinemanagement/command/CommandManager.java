package com.airlinemanagement.command;

import java.util.Stack;
import java.util.concurrent.ExecutionException;

public class CommandManager {
    private Stack<Command> undoStack=new Stack<>();
    private Stack<Command> redoStack=new Stack<>();

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
            System.out.println("Nothing to undo!");
        }
    }
}
