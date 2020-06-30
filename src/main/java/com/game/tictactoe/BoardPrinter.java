package com.game.tictactoe;

import java.io.PrintStream;

public class BoardPrinter {

    private final PrintStream printStream;

    public BoardPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    private static String markToString(Player player) {
        return player == null ? " " : player.toString();
    }

    public void printGameBoard(GameBoard board) {
        printRow(0, board);
        printStream.println("-+-+-");
        printRow(1, board);
        printStream.println("-+-+-");
        printRow(2, board);
    }

    private void printRow(int row, GameBoard board) {
        printStream.printf("%s|%s|%s\n", markToString(board.getSpot(row, 0)),
                markToString(board.getSpot(row, 1)), markToString(board.getSpot(row, 2)));
    }

}
