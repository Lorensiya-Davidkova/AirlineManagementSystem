package com.airlinemanagement.command;

import com.airlinemanagement.Status;

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
           return undoStack.peek().getUndoDisplayText();
       }
        return "Undo (no action)";
    }
}
