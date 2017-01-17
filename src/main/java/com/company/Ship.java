package com.company;

public abstract class Ship {

    enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private Field[] occupied;
    private int hits;
    private Orientation orientation;

    public Ship(Orientation orientation) {
        occupied = new Field[initializeOccupied(this.getClass())];
        this.orientation = orientation;
    }

    private int initializeOccupied(Class<? extends Ship> aClass) {
        if (aClass == Destroyer.class)
            return 1;
        else if (aClass == Submarine.class)
            return 2;
        else if (aClass == Cruiser.class)
            return 3;
        else
            return 4;
    }

    public boolean isSunk() {
        return hits == getDecksCount();
    }

    public void setOnField(Field field, int deckNo) {
        field.setState(State.SHIP);
        field.setShip(this);
        occupied[deckNo] = field;
    }

    public void hit() {
        hits++;
        if(isSunk()) {
            for (int i = 0; i < getDecksCount(); i++) {
                occupied[i].setState(State.SUNK);
            }
        }
    }

    public abstract int getDecksCount();

    public Orientation getOrientation() {
        return orientation;
    }

}

class Destroyer extends Ship {

    public Destroyer() {
        super(Orientation.HORIZONTAL);
    }

    @Override
    public int getDecksCount() {
        return 1;
    }
}

class Submarine extends Ship {
    public Submarine(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 2;
    }
}

class Cruiser extends Ship {
    public Cruiser(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 3;
    }
}

class Battleship extends Ship {
    public Battleship(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 4;
    }
}
