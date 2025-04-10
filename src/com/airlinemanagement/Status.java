package com.airlinemanagement;



public class Status {
    private StatusType type;
    private String message;

    private Status(StatusType type, String message){
        this.type=type;
        this.message=message;
    }

    public static Status success(String message){
        return new Status(StatusType.SUCCESS,message);
    }
    public static Status error(String message){
        return new Status(StatusType.ERROR,message);
    }
    public static Status warning(String message){
        return new Status(StatusType.WARNING,message);
    }
    public StatusType getType(){
        return this.type;
    }
    public String getMessage(){
        return this.message;
    }
}
