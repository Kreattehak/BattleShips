package com.company;

import java.util.Random;

class Board {

    private static final int BOARD_SIZE = 10;
    private static final int SHIP_TYPES_COUNT = 4;

    private int shipsCount;
    private int shipsCountByDecks[] = new int[SHIP_TYPES_COUNT];
    private Field[][] fields = new Field[BOARD_SIZE][BOARD_SIZE];

    Board() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                fields[y][x] = new Field(y, x, State.EMPTY);
            }
        }
    }

    private void printBoardLetters() {
        System.out.print("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(" " + (char) ('A' + i));
        }
        System.out.println();
    }

    void printBoard() {
        printBoardLetters();
        for (int y = 0; y < BOARD_SIZE; y++) {
            int numberToPrint = y + 1;
            if (numberToPrint < 10) {
                System.out.print(' ');
            }
            System.out.print(numberToPrint);

            for (int x = 0; x < BOARD_SIZE; x++) {
                char shipState = fields[y][x].getStateAsChar();
                System.out.print(" " + shipState);
            }
            System.out.println();
        }
    }

    void fillBoard() {
        Random random = new Random();

        for (int decks = 1; decks <= SHIP_TYPES_COUNT; decks++) {
            for (int i = 0; i < getCountOfShipDecks(decks); i++) {
                boolean shipPlaced = false;
                while (!shipPlaced) {
                    int y = random.nextInt(BOARD_SIZE);
                    int x = random.nextInt(BOARD_SIZE);
                    Ship.Orientation orientation = random.nextBoolean() ? Ship.Orientation.VERTICAL : Ship.Orientation.HORIZONTAL;
                    Ship ship = getShip(decks, orientation);
                    try {
                        addShip(y, x, ship);
                        shipPlaced = true;
                    } catch (IllegalMoveException e) {
                        e.getMessage();
                    }
                }
            }
        }
    }

    private Ship getShip(int decks, Ship.Orientation orientation) {
        switch (decks) {
            case 1:
                return new Destroyer();
            case 2:
                return new Submarine(orientation);
            case 3:
                return new Cruiser(orientation);
            default:
                return new Battleship(orientation);
        }
    }

    void addShip(int y, int x, Ship ship) throws IllegalMoveException {
        int index = ship.getDecksCount() - 1;

        if (shipsCountByDecks[index] == getCountOfShipDecks(ship.getDecksCount())) {
            throw new IllegalMoveException("All ships of that type are already placed!");
        }

        int yToSet = y;
        int xToSet = x;

        Field[] fieldsOccupiedByShip = new Field[ship.getDecksCount()];

        for (int i = 0; i <= index; i++) {
            if (ship.getOrientation() == Ship.Orientation.HORIZONTAL) {
                xToSet = x + i;
            } else {
                yToSet = y + i;
            }
            if (isOutside(yToSet, xToSet)) {
                throw new IllegalMoveException("You are trying to place a ship outside the board!");
            }
            fieldsOccupiedByShip[i] = fields[yToSet][xToSet];
        }

        for (int i = 0; i <= index; i++) {
            if (checkIsFieldOccupied(fieldsOccupiedByShip[i])) {
                throw new IllegalMoveException("Fields near to ship are already occupied!");
            }
        }

        for (int i = 0; i <= index; i++) {
            ship.setOnField(fieldsOccupiedByShip[i], i);
        }

        shipsCount++;
        shipsCountByDecks[index]++;
    }

    private boolean checkIsFieldOccupied(Field field) {
        for (int y = field.getY() - 1; y <= field.getY() + 1; y++) {
            for (int x = field.getX() - 1; x <= field.getX() + 1; x++) {
                if (isOutside(y, x)) {
                    continue;
                }
                if (fields[y][x].getState() != State.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    String shoot(int y, int x) throws IllegalMoveException {
        Field field = fields[y][x];

        if (field.getState() != State.EMPTY && field.getState() != State.SHIP) {
            throw new IllegalMoveException("You had already shot that place, check the map!");
        } else if (field.getState() == State.EMPTY) {
            field.setState(State.MISS);
            return String.format("Field[%c][%d] was empty", (char) (field.getX() + 65), field.getY() + 1);
        } else {
            field.setState(State.HIT);
            field.getShip().hit();
            String hitMark = String.format("Field[%c][%d] was a hit", (char) (field.getX() + 65), field.getY() + 1);
            if (field.getShip().isSunk()) {
                shipsCount--;
                return hitMark + " that hit sunked a " + field.getShip().getClass().getSimpleName();
            }
            return hitMark;
        }
    }

    boolean isOutside(int y, int x) {
        return x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE;
    }

    private int getCountOfShipDecks(int decks) {
        return SHIP_TYPES_COUNT - decks + 1;
    }

    Field getField(int y, int x) {
        return fields[y][x];
    }

    int getShipsCount() {
        return shipsCount;
    }
}
