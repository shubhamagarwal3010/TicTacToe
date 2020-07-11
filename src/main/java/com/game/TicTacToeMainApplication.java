package com.game;

import com.game.ai.ComputerPlayer;
import com.game.ai.GameEvaluator;
import com.game.ai.IComputerPlayer;
import com.game.tictactoe.BoardSize;
import com.game.tictactoe.GameRunner;
import com.game.tictactoe.Player;

import java.io.PrintStream;
import java.util.Scanner;

public class TicTacToeMainApplication {

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        PrintStream printStream = System.out;
        BoardSize boardSize = new BoardSize(printStream, inputScanner);
        int size = boardSize.getBoardSize();
        GameEvaluator evaluator = new GameEvaluator(Player.O);
        IComputerPlayer computerPlayer = new ComputerPlayer(evaluator);
        GameRunner game = new GameRunner(computerPlayer, inputScanner, printStream, size, Player.X);

        game.choosePlayerType();
    }
}
