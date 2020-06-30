package com.game.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private static int size;
    private final Player[][] board;

    public GameBoard(int size) {
        GameBoard.size = size;
        board = new Player[size][size];
    }

    public GameBoard(GameBoard other) {
        board = new Player[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = other.board[row][col];
            }
        }
    }

    public static int getSize() {
        return size;
    }

    private static void validatePosition(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Spot [" + row + "," + col + "] is outside acceptable limit");
        }
    }

    public boolean addSpot(int row, int col, Player player) {
        validatePosition(row, col);
        if (board[row][col] == null) {
            board[row][col] = player;
            return true;
        } else {
            return false;
        }
    }

    public Player getSpot(int row, int col) {
        validatePosition(row, col);
        return board[row][col];
    }

    public List<Cell> getAvailableSpots() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == null) {
                    cells.add(new Cell(row, col));
                }
            }
        }
        return cells;
    }
}
