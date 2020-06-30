package com.game.tictactoe;

import com.game.ai.IComputerPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameRunnerTest {

    @Mock
    private IComputerPlayer aiPlayer;
    @Mock
    private PrintStream printStream;

    @Test
    public void moveHumanContinuesToAcceptInputUntilValid() {
        Scanner scanner = scannerWithInputs("", " 1 1", "invalid", "-1,1", "3,1", "1,2,3", "0,0");
        GameRunner runner = new GameRunner(aiPlayer, scanner, printStream, 3, Player.X);

        runner.moveHuman(runner.getGameState().getCurrentPlayer());

        verify(printStream, times(6)).println(GameRunner.GAME_INSTRUCTION);
    }

    @Test
    public void moveHumanErrorWhenOffBoard() {
        Scanner scanner = scannerWithInputs("-1,0", "3,3", "0,0");
        GameRunner runner = new GameRunner(aiPlayer, scanner, printStream, 3, Player.X);

        runner.moveHuman(runner.getGameState().getCurrentPlayer());

        verify(printStream).printf("(%d,%d) is not on the board. ", -1, 0);
        verify(printStream).printf("(%d,%d) is not on the board. ", 3, 3);
        verify(printStream, times(2)).println(GameRunner.GAME_INSTRUCTION);
    }

    @Test
    public void moveHumanErrorWhenRepeatMove() {
        Scanner scanner = scannerWithInputs("1,1", "1,1", "0,0");
        GameRunner runner = new GameRunner(aiPlayer, scanner, printStream, 3, Player.X);

        runner.moveHuman(runner.getGameState().getCurrentPlayer());
        runner.moveHuman(runner.getGameState().getCurrentPlayer());

        verify(printStream).printf("(%d,%d) has already been taken. ", 1, 1);
        verify(printStream).println(GameRunner.GAME_INSTRUCTION);
    }

    @Test
    public void moveHumanSwitchesPlayers() {
        Scanner scanner = scannerWithInputs("1,1", "0,0");
        GameRunner runner = new GameRunner(aiPlayer, scanner, printStream, 3, Player.X);

        assertEquals(runner.getGameState().getCurrentPlayer(), Player.X);
        runner.moveHuman(runner.getGameState().getCurrentPlayer());
        assertEquals(runner.getGameState().getCurrentPlayer(), Player.O);
    }

    @Test
    public void moveComputerSwitchesPlayers() {
        GameRunner runner = new GameRunner(aiPlayer, new Scanner(""), printStream, 3, Player.X);
        GameState nextState = mock(GameState.class);
        when(nextState.getLastMove()).thenReturn(new Cell(0, 0));
        when(aiPlayer.evaluateBestMove(Mockito.any(GameState.class))).thenReturn(nextState);

        assertEquals(runner.getGameState().getCurrentPlayer(), Player.X);
        runner.moveComputer();
        assertEquals(runner.getGameState().getCurrentPlayer(), Player.O);
    }

    private Scanner scannerWithInputs(String... inputs) {
        String joinedInputs = String.join("\n", inputs);
        return new Scanner(joinedInputs);
    }
}
