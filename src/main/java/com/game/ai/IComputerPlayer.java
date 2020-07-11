package com.game.ai;

import com.game.tictactoe.GameState;

public interface IComputerPlayer {

    void setGameLevel(GameLevel gameLevel);

    GameState evaluateAIMove(GameState currentState);

}
