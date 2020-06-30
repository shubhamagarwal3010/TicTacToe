package com.game.tictactoe;

import java.util.List;

public interface IGameState {

    List<GameState> availableStatesForAIPlayer();

    boolean isOver();
}
