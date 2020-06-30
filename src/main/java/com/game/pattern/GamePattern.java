package com.game.pattern;

import com.game.tictactoe.GameBoard;
import com.game.tictactoe.Player;

public interface GamePattern {

    boolean matches(GameBoard game, Player player);
}
