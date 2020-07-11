package com.game.ai;

import com.game.tictactoe.GameState;
import com.game.tictactoe.Player;

public class GameEvaluator {

    private final Player player;

    public GameEvaluator(Player player) {
        this.player = player;
    }

    public int evaluateGameScore(GameState game) {
        if (game.hasWin(player)) {
            return game.availableStatesForNextPlayer().size() + 1;
        } else if (game.hasWin(Player.getNextPlayer(player))) {
            return -1;
        } else {
            return 0;
        }
    }
}
