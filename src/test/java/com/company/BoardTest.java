package com.company;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import com.company.Ship.Orientation;

public class BoardTest {

    private Board board;
    public static final int ONE_SHIP = 1;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @After
    public void tearDown() throws Exception {
        board = null;
    }

    @Test
    public void shouldAddDestroyerToShipsCount() throws Exception {
        board.addShip(0, 0, new Destroyer());
        assertThat("Ship was not added", board, hasProperty("shipsCount", equalTo(ONE_SHIP)));
    }

    @Test
    public void shouldAddDestroyerOnBoard() throws Exception {
        board.addShip(0, 0, new Destroyer());
        Field field = board.getField(0, 0);
        assertEquals("Ships was not added at field[0][0]", State.SHIP, field.getState());
    }

    @Test
    public void shouldAddSubmarineOnBoard() throws Exception {
        board.addShip(0, 0, new Submarine(Orientation.HORIZONTAL));
        Field field = board.getField(0, 1);
        assertEquals("Ships was not added at field[0][1]", State.SHIP, field.getState());
    }

    @Test
    public void shouldNotBeAbleToGetOutsideVertically() throws Exception {
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("outside");
        board.addShip(7, 1, new Battleship(Orientation.VERTICAL));
    }

    @Test
    public void shouldNotBeAbleToGetOutsideHorizontally() throws Exception {
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("outside");
        board.addShip(1, 7, new Battleship(Orientation.HORIZONTAL));
    }

    @Test
    public void shipShouldNotBePlacedNextToEachOtherVertically() throws Exception {
        board.addShip(5, 5, new Cruiser(Orientation.VERTICAL));
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("near to ship");
        board.addShip(8, 5, new Cruiser(Orientation.HORIZONTAL));
    }

    @Test
    public void shipShouldNotBePlacedNextToEachOtherHorizontally() throws Exception {
        board.addShip(5, 5, new Cruiser(Orientation.HORIZONTAL));
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("near to ship");
        board.addShip(5, 8, new Cruiser(Orientation.VERTICAL));
    }

    @Test
    public void shouldNotBeAbleToAddFiveDestroyers() throws Exception {
        board.addShip(0, 0, new Destroyer());
        board.addShip(0, 2, new Destroyer());
        board.addShip(0, 4, new Destroyer());
        board.addShip(0, 6, new Destroyer());
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("All ships");
        board.addShip(0, 8, new Destroyer());
    }

    @Test
    public void shouldNotBeAbleToAddTwoBattleships() throws Exception {
        board.addShip(0, 0, new Battleship(Orientation.VERTICAL));
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("All ships");
        board.addShip(0, 4, new Battleship(Orientation.VERTICAL));
    }

    @Test
    public void shouldNotBeAbleToAddOutsideBoardBeforeXIndexLowerThanZero() throws Exception {
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("outside");
        board.addShip(0, -1, new Destroyer());
    }

    @Test
    public void shouldNotBeAbleToAddOutsideBoardBeforeXIndexOverTen() throws Exception {
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("outside");
        board.addShip(0, 10, new Destroyer());
    }

    @Test
    public void shouldNotBeAbleToAddOutsideBoardBeforeYIndexLowerThanZero() throws Exception {
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("outside");
        board.addShip(-1, 0, new Destroyer());
    }

    @Test
    public void shouldNotBeAbleToAddOutsideBoardBeforeYIndexOverTen() throws Exception {
        exception.expect(IllegalMoveException.class);
        exception.expectMessage("outside");
        board.addShip(10, 0, new Destroyer());
    }

    @Test
    public void shouldMarkFieldAsHit() throws Exception {
        board.addShip(0,0, new Submarine(Orientation.HORIZONTAL));
        board.shoot(0,0);
        Field field = board.getField(0,0);
        assertEquals("Field[0][0] was not marked as hit", State.HIT, field.getState());
    }

    @Test
    public void shouldMarkFieldAsMiss() throws Exception {
        board.shoot(0,0);
        Field field = board.getField(0,0);
        assertEquals("Field[0][0] was not marked as missed", State.MISS, field.getState());
    }

    @Test
    public void shouldMarkFieldsSunk() throws Exception {
        Ship ship = new Battleship(Orientation.VERTICAL);
        board.addShip(0, 0, ship);
        board.shoot(0,0);
        board.shoot(1,0);
        board.shoot(2,0);
        board.shoot(3,0);

        Field[] fields = new Field[ship.getDecksCount()];

        for (int i = 0; i < ship.getDecksCount(); i++) {
            fields[i] = board.getField(i, 0);
        }
        for (int i = 0; i < ship.getDecksCount(); i++) {
            assertEquals("Fields that are occupied by ship weren't marked as sunk", State.SUNK, fields[i].getState());
        }
    }

    @Test
    public void shouldAddAllShips() throws Exception {
        board.fillBoard();
        assertThat(board, hasProperty("shipsCount", equalTo(10)));
    }
}