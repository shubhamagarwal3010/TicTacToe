package com.game.tictactoe;

public class Cell {
    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cell)) {
            return false;
        }
        Cell other = (Cell) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public String toString() {
        return "Cell: " + '(' + row + ',' + col + ')';
    }
}
