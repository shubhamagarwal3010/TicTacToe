package com.game.tictactoe;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardPrinter {

    private final PrintStream printStream;

    public BoardPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    private static String markToString(Player player) {
        return player == null ? " " : player.toString();
    }

    public void printGameBoard(GameBoard board) {
        IntStream.range(0, GameBoard.getSize()).forEach(i -> {
            if (i < GameBoard.getSize() - 1) {
                printRow(i, board);
                lineSeparator();
            } else {
                printRow(i, board);
            }
        });
    }

    private void printRow(int row, GameBoard board) {
        List<String> spots = IntStream.range(0, GameBoard.getSize()).mapToObj(i -> markToString(board.getSpot(row, i))).collect(Collectors.toList());
        printStream.print(String.join("|", spots) + "\n");
    }

    private void lineSeparator() {
        List<String> spots = IntStream.range(0, GameBoard.getSize()).mapToObj(i -> "-").collect(Collectors.toList());
        printStream.println(String.join("+", spots));
    }
}
