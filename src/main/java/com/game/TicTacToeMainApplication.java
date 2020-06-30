package com.game;

import com.game.tictactoe.GameRunner;
import com.game.tictactoe.Player;

import java.util.Scanner;

public class TicTacToeMainApplication {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        GameRunner game = new GameRunner(inputScanner, System.out, 3, Player.X);

        game.choosePlayerType();
    }
}