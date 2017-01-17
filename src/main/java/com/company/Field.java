package com.company;

public class Field {

    private final int y, x;
    private State state;
    private Ship ship;

    public Field(int y, int x, State state) {
        this.y = y;
        this.x = x;
        this.state = state;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public char getStateAsChar() {
        return state.getState();
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

}
