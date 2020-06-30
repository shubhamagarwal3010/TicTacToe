package com.game.ai;

import com.game.tictactoe.GameState;

public interface IComputerPlayer {

    GameState evaluateBestMove(GameState currentState);

}
