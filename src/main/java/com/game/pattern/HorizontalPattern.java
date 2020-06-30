package com.game.pattern;

import com.game.tictactoe.GameBoard;
import com.game.tictactoe.Player;

public class HorizontalPattern implements GamePattern {

    @Override
    public boolean matches(GameBoard game, Player player) {
        int numOfMatches;

        for (int row = 0; row < GameBoard.getSize(); row++) {
            numOfMatches = 0;

            for (int column = 0; column < GameBoard.getSize(); column++) {
                if (game.getSpot(row, column) == player) {
                    numOfMatches++;
                }
            }

            if (numOfMatches == GameBoard.getSize()) {
                return true;
            }
        }

        return false;
    }
}
