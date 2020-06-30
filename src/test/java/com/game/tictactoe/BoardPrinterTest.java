package com.game.tictactoe;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;

import static com.game.tictactoe.Player.O;
import static com.game.tictactoe.Player.X;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class BoardPrinterTest {
    private BoardPrinter printer;
    @Mock
    private PrintStream printStream;

    @Before
    public void setup() {
        printer = new BoardPrinter(printStream);
    }

    @Test
    public void printGameBoardEmpty() {
        GameBoard board = new GameBoard(3);

        printer.printGameBoard(board);

        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream).printf("%s|%s|%s\n", " ", " ", " ");
        inOrder.verify(printStream).println("-+-+-");
        inOrder.verify(printStream).printf("%s|%s|%s\n", " ", " ", " ");
        inOrder.verify(printStream).println("-+-+-");
        inOrder.verify(printStream).printf("%s|%s|%s\n", " ", " ", " ");
    }

    @Test
    public void printGameBoard() {
        GameBoard gameBoard = new GameBoard(3);

        gameBoard.addSpot(0, 0, O);
        gameBoard.addSpot(0, 1, X);
        gameBoard.addSpot(0, 2, O);
        gameBoard.addSpot(1, 0, X);
        gameBoard.addSpot(1, 2, O);
        gameBoard.addSpot(2, 0, X);
        gameBoard.addSpot(2, 1, O);
        gameBoard.addSpot(2, 2, X);
        printer.printGameBoard(gameBoard);

        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream).printf("%s|%s|%s\n", "O", "X", "O");
        inOrder.verify(printStream).println("-+-+-");
        inOrder.verify(printStream).printf("%s|%s|%s\n", "X", " ", "O");
        inOrder.verify(printStream).println("-+-+-");
        inOrder.verify(printStream).printf("%s|%s|%s\n", "X", "O", "X");
    }
}
