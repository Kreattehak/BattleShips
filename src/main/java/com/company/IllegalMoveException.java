package com.company;

class IllegalMoveException extends Exception {

    IllegalMoveException() {
        this("Default IllegalMoveException message");
    }

    IllegalMoveException(String message) {
        super(message);
    }
}
