package com.game.pattern;

import com.game.tictactoe.GameBoard;
import com.game.tictactoe.Player;

public class BottomLeftToTopRightPattern implements GamePattern {

    @Override
    public boolean matches(GameBoard game, Player player) {
        int numOfMatches = 0;
        int column = 0;

        for (int row = (GameBoard.getSize() - 1); row >= 0; row--) {
            if (game.getSpot(row, column) == player) {
                numOfMatches++;
            }

            column++;
        }

        return numOfMatches == GameBoard.getSize();
    }
}
