package com.company;

abstract class Ship {

    enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private Field[] occupied;
    private int hits;
    private Orientation orientation;

    Ship(Orientation orientation) {
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

    boolean isSunk() {
        return hits == getDecksCount();
    }

    void setOnField(Field field, int deckNo) {
        field.setState(State.SHIP);
        field.setShip(this);
        occupied[deckNo] = field;
    }

    void hit() {
        hits++;
        if (isSunk()) {
            for (int i = 0; i < getDecksCount(); i++) {
                occupied[i].setState(State.SUNK);
            }
        }
    }

    abstract int getDecksCount();

    Orientation getOrientation() {
        return orientation;
    }
}

class Destroyer extends Ship {

    Destroyer() {
        super(Orientation.HORIZONTAL);
    }

    @Override
    public int getDecksCount() {
        return 1;
    }
}

class Submarine extends Ship {
    Submarine(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 2;
    }
}

class Cruiser extends Ship {
    Cruiser(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 3;
    }
}

class Battleship extends Ship {
    Battleship(Orientation orientation) {
        super(orientation);
    }

    @Override
    public int getDecksCount() {
        return 4;
    }
}
