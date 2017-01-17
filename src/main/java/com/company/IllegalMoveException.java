package com.company;


public class IllegalMoveException extends Exception {

    public IllegalMoveException() {
        this("Default IllegalMoveException message");
    }

    public IllegalMoveException(String message) {
        super(message);
    }
}
