package com.game.tictactoe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class GameBoardTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GameBoard board;

    @Before
    public void setup() {
        board = new GameBoard(3);
    }

    // -- getSpot
    @Test
    public void getMarkUnmarked() {
        assertNull(board.getSpot(0, 0));
    }

    @Test
    public void getMarkOffBoard() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Spot [3,0] is outside acceptable limit");
        board.getSpot(3, 0);
    }

    @Test
    public void getMarkOffBoardNegative() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Spot [-1,0] is outside acceptable limit");
        board.getSpot(-1, 0);
    }

    // -- addSpot

    @Test
    public void markOnBoard() {
        boolean success = board.addSpot(0, 0, Player.O);

        assertTrue(success);
        assertEquals(board.getSpot(0, 0), Player.O);
    }

    @Test
    public void markTwice() {
        board.addSpot(0, 0, Player.O);
        boolean success = board.addSpot(0, 0, Player.X);

        assertFalse(success);
        assertEquals(board.getSpot(0, 0), Player.O);
    }

    @Test
    public void markOffBoard() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Spot [3,0] is outside acceptable limit");
        board.addSpot(3, 0, null);
    }

    @Test
    public void getAllAvailableSpots() {
        assertTrue(board.getAvailableSpots().containsAll(Arrays.asList(new Cell(0, 0), new Cell(0, 1),
                new Cell(0, 2), new Cell(1, 0), new Cell(1, 1), new Cell(1, 2),
                new Cell(2, 0), new Cell(2, 1), new Cell(2, 2))));
    }

    @Test
    public void getLeftoverSpots() {
        board.addSpot(0, 0, Player.X);
        assertTrue(board.getAvailableSpots().containsAll(Arrays.asList(new Cell(0, 1), new Cell(0, 2),
                new Cell(1, 0), new Cell(1, 1), new Cell(1, 2), new Cell(2, 0),
                new Cell(2, 1), new Cell(2, 2))));
    }
}
