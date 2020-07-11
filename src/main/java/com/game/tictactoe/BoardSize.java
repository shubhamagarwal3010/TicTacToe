package com.game.tictactoe;

import java.io.PrintStream;
import java.util.Scanner;

public class BoardSize {

    static final String CHOOSE_BOARD_SIZE_INSTRUCTION = "Type 3 for 3x3 board or type 4 for 4x4 board size.";
    private final PrintStream printStream;
    private final Scanner scanner;

    public BoardSize(PrintStream printStream, Scanner scanner) {
        this.printStream = printStream;
        this.scanner = scanner;
    }

    public int getBoardSize() {
        int boardSize;
        while (true) {
            do {
                showBoardSizeInstruction();
                boardSize = parseIntegerInput(scanner.nextLine());
            } while (boardSize == 0);
            if (boardSize == 3 || boardSize == 4) {
                return boardSize;
            }
        }
    }


    private int parseIntegerInput(String input) {
        if (input.trim().length() != 1) {
            return 0;
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showBoardSizeInstruction() {
        printStream.println(CHOOSE_BOARD_SIZE_INSTRUCTION);
    }
}
