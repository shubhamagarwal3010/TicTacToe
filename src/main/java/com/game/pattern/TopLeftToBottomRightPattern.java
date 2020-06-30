package com.game.pattern;

import com.game.tictactoe.GameBoard;
import com.game.tictactoe.Player;

public class TopLeftToBottomRightPattern implements GamePattern {

    @Override
    public boolean matches(GameBoard game, Player player) {
        int numOfMatches = 0;

        for (int i = 0; i < GameBoard.getSize(); i++) {
            if (game.getSpot(i, i) == player) {
                numOfMatches++;
            }
        }

        return numOfMatches == GameBoard.getSize();
    }
}
