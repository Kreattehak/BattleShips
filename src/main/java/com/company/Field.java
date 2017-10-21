package com.company;

class Field {

    private final int y, x;
    private State state;
    private Ship ship;

    Field(int y, int x, State state) {
        this.y = y;
        this.x = x;
        this.state = state;
    }

    int getY() {
        return y;
    }

    int getX() {
        return x;
    }

    State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }

    char getStateAsChar() {
        return state.getState();
    }

    Ship getShip() {
        return ship;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }
}
