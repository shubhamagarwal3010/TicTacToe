package com.game.tictactoe;

public class GameEvaluator {

    private final Player player;

    public GameEvaluator(Player player) {
        this.player = player;
    }

    public int evaluateAIPlayer(GameState game) {
        if (game.hasWin(player)) {
            return game.availableStatesForAIPlayer().size() + 1;
        } else if (game.hasWin(Player.getNextPlayer(player))) {
            return -1;
        } else {
            return 0;
        }
    }
}
