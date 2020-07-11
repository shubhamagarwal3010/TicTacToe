package com.game.ai;

import com.game.tictactoe.GameState;
import com.game.tictactoe.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameEvaluatorTest {

    private GameEvaluator evaluator;
    @Mock
    private GameState game;
    @Mock
    private List<GameState> availableStates;

    @Before
    public void setup() {
        evaluator = new GameEvaluator(Player.X);
    }

    @Test
    public void evaluateWin() {
        when(game.hasWin(Player.X)).thenReturn(true);
        assertEquals(evaluator.evaluateGameScore(game), 1);
    }

    @Test
    public void evaluateWinConsidersAvailableMoves() {
        when(game.hasWin(Player.X)).thenReturn(true);
        when(game.availableStatesForNextPlayer()).thenReturn(availableStates);
        when(availableStates.size()).thenReturn(5);
        assertEquals(evaluator.evaluateGameScore(game), 6);
    }

    @Test
    public void evaluateLoss() {
        when(game.hasWin(Player.O)).thenReturn(true);
        assertEquals(evaluator.evaluateGameScore(game), -1);
    }

    @Test
    public void evaluateDraw() {
        assertEquals(evaluator.evaluateGameScore(game), 0);
    }
}
