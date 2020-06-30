package com.game.tictactoe;

public enum Player {
    O,
    X;

    public static Player getNextPlayer(Player player) {
        return player == X ? O : X;
    }
}
