package com.airlinemanagement.repository;

import com.airlinemanagement.Status;

public class Result<T> {
    private final T data;
    private final Status status;

    public Result(T data, Status status) {
        this.data = data;
        this.status = status;
    }
    public T getData(){return data;}
    public Status getStatus(){return status;}
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(data, Status.success(message));
    }

    public static <T> Result<T> warning(T data, String message) {
        return new Result<>(data, Status.warning(message));
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(null, Status.error(message));
    }
}
