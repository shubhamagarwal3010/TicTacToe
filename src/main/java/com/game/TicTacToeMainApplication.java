package com.game;

import com.game.ai.ComputerPlayer;
import com.game.ai.GameEvaluator;
import com.game.ai.IComputerPlayer;
import com.game.tictactoe.GameRunner;
import com.game.tictactoe.Player;

import java.util.Scanner;

public class TicTacToeMainApplication {

    public static void main(String[] args) {
        GameEvaluator evaluator = new GameEvaluator(Player.O);
        IComputerPlayer computerPlayer = new ComputerPlayer(evaluator);
        Scanner inputScanner = new Scanner(System.in);
        GameRunner game = new GameRunner(computerPlayer, inputScanner, System.out, 3, Player.X);

        game.choosePlayerType();
    }
}
