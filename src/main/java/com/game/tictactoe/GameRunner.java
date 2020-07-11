package com.game.tictactoe;

import com.game.ai.IComputerPlayer;

import java.io.PrintStream;
import java.util.Scanner;

public class GameRunner {

    static final String GAME_INSTRUCTION = "Enter '<row>,<col>' to play a position. For example, '0,2'.";
    static final String CHOOSE_PLAYER_INSTRUCTION = "Type 1 to play with Human or type 2 to play with computer.";

    private final GameState gameState;

    private final BoardPrinter boardPrinter;
    private final IComputerPlayer computerPlayer;
    private final Scanner scanner;
    private final PrintStream printStream;

    public GameRunner(IComputerPlayer computerPlayer, Scanner scanner, PrintStream printStream, int boardSize, Player startingPlayer) {
        this.gameState = new GameState(boardSize, startingPlayer);
        this.boardPrinter = new BoardPrinter(printStream);
        this.computerPlayer = computerPlayer;
        this.scanner = scanner;
        this.printStream = printStream;
    }

    public void playWithComputer() {
        showGameInstruction();
        while (!gameState.isOver()) {
            moveHuman(gameState.getCurrentPlayer());
            moveComputer();
            boardPrinter.printGameBoard(gameState.getGameBoard());
        }
        printGameOver();
    }

    public void playWithHuman() {
        showGameInstruction();
        while (!gameState.isOver()) {
            moveHuman(gameState.getCurrentPlayer());
            moveHuman(gameState.getCurrentPlayer());
            boardPrinter.printGameBoard(gameState.getGameBoard());
        }
        printGameOver();
    }

    GameState getGameState() {
        return gameState;
    }

    void moveComputer() {
        GameState bestMove = computerPlayer.evaluateBestMove(gameState);
        if (bestMove == null) {
            return;
        }
        Cell nextMove = bestMove.getLastMove();
        gameState.play(nextMove.getRow(), nextMove.getCol());
        gameState.switchPlayer();
    }

    public void choosePlayerType() {
        int playerType;
        while (true) {
            do {
                showChoosePlayerInstruction();
                playerType = parsePlayerType(scanner.nextLine());
            } while (playerType == 0);
            if (playerType == 1) {
                playWithHuman();
                return;
            } else if (playerType == 2) {
                playWithComputer();
                return;
            }
        }
    }

    void moveHuman(Player player) {
        Cell cell;
        while (true) {
            do {
                printStream.printf("Player %s [row,col]: ", player.toString());
                cell = parsePlayerInput(scanner.nextLine());
            } while (cell == null);

            try {
                if (gameState.play(cell.getRow(), cell.getCol())) {
                    gameState.switchPlayer();
                    return;
                } else {
                    printStream.printf("(%d,%d) has already been taken. ", cell.getRow(), cell.getCol());
                    showGameInstruction();
                }
            } catch (IllegalArgumentException e) {
                printStream.printf("(%d,%d) is not on the board. ", cell.getRow(), cell.getCol());
                showGameInstruction();
            }
        }
    }

    private void printGameOver() {
        if (gameState.hasWin(Player.X)) {
            printStream.println("Player X won.");
        } else if (gameState.hasWin(Player.O)) {
            printStream.println("Player O won.");
        } else {
            printStream.println("Game ended in a draw.");
        }
    }

    private Cell parsePlayerInput(String playerInput) {
        String[] inputs = playerInput.split(",");
        if (inputs.length != 2) {
            showGameInstruction();
            return null;
        }
        try {
            int row = Integer.parseInt(inputs[0].trim());
            int col = Integer.parseInt(inputs[1].trim());
            return new Cell(row, col);
        } catch (NumberFormatException e) {
            showGameInstruction();
            return null;
        }
    }

    private int parsePlayerType(String playerType) {
        if (playerType.trim().length() != 1) {
            return 0;
        }
        try {
            return Integer.parseInt(playerType.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showChoosePlayerInstruction() {
        printStream.println(CHOOSE_PLAYER_INSTRUCTION);
    }

    private void showGameInstruction() {
        printStream.println(GAME_INSTRUCTION);
    }
}
