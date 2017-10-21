package com.company;

public enum State {

    EMPTY(' '), MISS('?'), HIT('O'), SUNK('X'), SHIP(' ');

    private char state;

    State(char state) {
        this.state = state;
    }

    char getState() {
        return state;
    }
}
