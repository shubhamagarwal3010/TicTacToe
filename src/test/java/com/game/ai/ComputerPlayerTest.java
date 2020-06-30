package com.game.ai;

import com.game.tictactoe.GameBoard;
import com.game.tictactoe.GameEvaluator;
import com.game.tictactoe.GameState;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static com.game.tictactoe.Player.O;
import static com.game.tictactoe.Player.X;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComputerPlayerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ComputerPlayer aiPlayer;
    @Mock
    private GameEvaluator evaluator;
    @Mock
    private GameState gameState;

    @Before
    public void setup() {
        aiPlayer = new ComputerPlayer(evaluator);
    }

    // choose winning move
    @Test
    public void preferWinningState() {
        GameState winState = mock(GameState.class);
        GameState drawState = mock(GameState.class);
        when(winState.isOver()).thenReturn(true);
        when(drawState.isOver()).thenReturn(true);
        when(evaluator.evaluateAIPlayer(winState)).thenReturn(100);
        when(evaluator.evaluateAIPlayer(drawState)).thenReturn(0);
        when(gameState.availableStatesForAIPlayer()).thenReturn(Arrays.asList(winState, drawState));

        GameState actualState = aiPlayer.evaluateBestMove(gameState);
        assertEquals(actualState, winState);
    }

    //     prevent loss. this is essentially the same as above...
    @Test
    public void preventLosingMove() {
        GameState loseState = mock(GameState.class);
        GameState drawState = mock(GameState.class);
        when(loseState.isOver()).thenReturn(true);
        when(drawState.isOver()).thenReturn(true);
        when(evaluator.evaluateAIPlayer(loseState)).thenReturn(-1);
        when(evaluator.evaluateAIPlayer(drawState)).thenReturn(0);
        when(gameState.availableStatesForAIPlayer()).thenReturn(Arrays.asList(loseState, drawState));

        GameState actualState = aiPlayer.evaluateBestMove(gameState);
        assertEquals(actualState, drawState);
    }

    @Test
    public void preferEarlyWin() {
        GameBoard gameBoard = new GameBoard(3);

        gameBoard.addSpot(0, 0, O);
        gameBoard.addSpot(1, 0, O);
        gameBoard.addSpot(1, 1, X);
        gameBoard.addSpot(1, 2, X);
        gameBoard.addSpot(2, 2, X);
        GameState gameState = new GameState(gameBoard, O);
        ComputerPlayer agent = new ComputerPlayer(new GameEvaluator(O));
        GameState actualState = agent.evaluateBestMove(gameState);
        assertTrue(actualState.hasWin(O));
    }

    // /game already over
    @Test
    public void evaluateGameAlreadyOver() {
        when(gameState.isOver()).thenReturn(true);
        assertNull(aiPlayer.evaluateBestMove(gameState));
    }

}
