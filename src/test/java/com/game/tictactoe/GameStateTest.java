package com.game.tictactoe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameStateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GameState game;

    @Before
    public void setup() {
        game = new GameState(3, Player.X);
    }

    @Test
    public void startingPlayerIsX() {
        assertEquals(new GameState(3, Player.X).getCurrentPlayer(), Player.X);
    }

    @Test
    public void getAvailableStatesEmptyBoard() {
        GameState game = new GameState(3, Player.X);
        assertEquals(game.availableStatesForNextPlayer().size(), 9);
    }

    @Test
    public void getAvailableStatesLastOne() {
        GameState game = new GameState(3, Player.X);
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 0);
        game.play(2, 1);

        List<GameState> states = game.availableStatesForNextPlayer();
        assertEquals(states.size(), 1);
        GameState availableState = states.get(0);
        assertEquals(availableState.getCurrentPlayer(), Player.getNextPlayer(game.getCurrentPlayer()));
        assertEquals(availableState.getLastMove(), new Cell(2, 2));
    }

    @Test
    public void getAvailableStatesCompleteBoard() {
        GameState game = new GameState(3, Player.X);
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 0);
        game.play(2, 1);
        game.play(2, 2);

        assertTrue(game.availableStatesForNextPlayer().isEmpty());
    }

    @Test
    public void hasWinRow() {
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        assertTrue(game.hasWin(Player.X));
    }

    @Test
    public void hasWinCol() {
        game.play(0, 0);
        game.play(1, 0);
        game.play(2, 0);
        assertTrue(game.hasWin(Player.X));
    }

    @Test
    public void hasWinDiagonal() {
        game.play(0, 0);
        game.play(1, 1);
        game.play(2, 2);
        assertTrue(game.hasWin(Player.X));
    }

    @Test
    public void isOverWin() {
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        assertTrue(game.isOver());
    }

    @Test
    public void isOverDraw() {
        // XOX
        // OXX
        // OXO
        game.play(0, 0);
        game.play(0, 2);
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 1);
        game.switchPlayer();
        game.play(0, 1);
        game.play(1, 0);
        game.play(2, 0);
        game.play(2, 2);

        assertTrue(game.isOver());
    }

    @Test
    public void playOnBoard() {
        assertTrue(game.play(0, 0));
        assertEquals(game.getLastMove(), new Cell(0, 0));
    }

    @Test
    public void playOffBoard() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Spot [-1,0] is outside acceptable limit");
        game.play(-1, 0);
    }

    @Test
    public void playSameLocation() {
        assertTrue(game.play(0, 0));
        assertTrue(game.play(0, 1));
        // should not affect the last move
        assertFalse(game.play(0, 0));
        assertEquals(game.getLastMove(), new Cell(0, 1));
    }

    @Test
    public void switchPlayer() {
        assertEquals(game.getCurrentPlayer(), Player.X);
        game.switchPlayer();
        assertEquals(game.getCurrentPlayer(), Player.O);
        game.switchPlayer();
        assertEquals(game.getCurrentPlayer(), Player.X);
    }
}
