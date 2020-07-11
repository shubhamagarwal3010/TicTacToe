package com.game.ai;

import com.game.tictactoe.Cell;
import com.game.tictactoe.GameBoard;
import com.game.tictactoe.GameState;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static com.game.tictactoe.Player.O;
import static com.game.tictactoe.Player.X;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ComputerPlayer.class)
public class ComputerPlayerTest {

    @Test
    public void subRunner() {
        JUnitCore.runClasses(SubComputerPlayerTest.class);
    }

    @Test
    public void shouldAllowHumanToWinWithBeginnerGameLevel() {
        PowerMock.mockStatic(Math.class);
        EasyMock.expect(Math.random()).andReturn(0.0).anyTimes();
        PowerMock.replay(Math.class);

        GameBoard gameBoard = new GameBoard(3);
        gameBoard.addSpot(0, 0, O);
        gameBoard.addSpot(1, 0, O);
        gameBoard.addSpot(1, 1, X);
        gameBoard.addSpot(1, 2, X);
        gameBoard.addSpot(2, 2, X);
        GameState gameState = new GameState(gameBoard, O);
        ComputerPlayer agent = new ComputerPlayer(new GameEvaluator(O));
        agent.setGameLevel(GameLevel.BEGINNER);

        GameState actualState = agent.evaluateAIMove(gameState);
        assertFalse(actualState.hasWin(O));
    }

    @Test
    public void shouldNotAllowHumanToWinWithIntermediateLevelAndBestMove() {
        PowerMock.mockStatic(Math.class);
        EasyMock.expect(Math.random()).andReturn(0.49).anyTimes();
        PowerMock.replay(Math.class);

        GameBoard gameBoard = new GameBoard(3);
        gameBoard.addSpot(0, 0, O);
        gameBoard.addSpot(1, 0, O);
        gameBoard.addSpot(1, 1, X);
        gameBoard.addSpot(1, 2, X);
        gameBoard.addSpot(2, 2, X);
        GameState gameState = new GameState(gameBoard, O);
        ComputerPlayer agent = new ComputerPlayer(new GameEvaluator(O));
        agent.setGameLevel(GameLevel.INTERMEDIATE);
        GameState actualState = agent.evaluateAIMove(gameState);
        assertEquals(actualState.getLastMove(), new Cell(2, 0));
        assertTrue(actualState.hasWin(O));
    }

    @Test
    public void shouldNotAllowComputerToWinWithIntermediateLevelAndSecondOptimalMove() {
        PowerMock.mockStatic(Math.class);
        EasyMock.expect(Math.random()).andReturn(0.51).anyTimes();
        PowerMock.replay(Math.class);

        GameBoard gameBoard = new GameBoard(3);
        gameBoard.addSpot(0, 0, O);
        gameBoard.addSpot(1, 0, O);
        gameBoard.addSpot(1, 1, X);
        gameBoard.addSpot(1, 2, X);
        gameBoard.addSpot(2, 2, X);
        GameState gameState = new GameState(gameBoard, O);
        ComputerPlayer agent = new ComputerPlayer(new GameEvaluator(O));
        agent.setGameLevel(GameLevel.INTERMEDIATE);
        GameState actualState = agent.evaluateAIMove(gameState);
        assertFalse(actualState.hasWin(O));
    }

    @RunWith(MockitoJUnitRunner.StrictStubs.class)
    public static class SubComputerPlayerTest {

        @Rule
        public ExpectedException thrown = ExpectedException.none();
        private ComputerPlayer aiPlayer;
        @Mock
        private GameEvaluator evaluator;
        @Mock
        private GameState gameState;
        // choose winning move

        @Before
        public void setup() {
            aiPlayer = new ComputerPlayer(evaluator);
        }

        @Test
        public void preferWinningState() {
            GameState winState = mock(GameState.class);
            GameState drawState = mock(GameState.class);
            when(winState.isOver()).thenReturn(true);
            when(drawState.isOver()).thenReturn(true);
            when(evaluator.evaluateGameScore(winState)).thenReturn(100);
            when(evaluator.evaluateGameScore(drawState)).thenReturn(0);
            when(gameState.availableStatesForNextPlayer()).thenReturn(Arrays.asList(winState, drawState));

            GameState actualState = aiPlayer.evaluateAIMove(gameState);
            assertEquals(actualState, winState);
        }

        //     prevent loss. this is essentially the same as above...
        @Test
        public void preventLosingMove() {
            GameState loseState = mock(GameState.class);
            GameState drawState = mock(GameState.class);
            when(loseState.isOver()).thenReturn(true);
            when(drawState.isOver()).thenReturn(true);
            when(evaluator.evaluateGameScore(loseState)).thenReturn(-1);
            when(evaluator.evaluateGameScore(drawState)).thenReturn(0);
            when(gameState.availableStatesForNextPlayer()).thenReturn(Arrays.asList(loseState, drawState));

            GameState actualState = aiPlayer.evaluateAIMove(gameState);
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
            GameState actualState = agent.evaluateAIMove(gameState);
            assertTrue(actualState.hasWin(O));
        }

        // /game already over
        @Test
        public void evaluateGameAlreadyOver() {
            when(gameState.isOver()).thenReturn(true);
            assertNull(aiPlayer.evaluateAIMove(gameState));
        }
    }
}
