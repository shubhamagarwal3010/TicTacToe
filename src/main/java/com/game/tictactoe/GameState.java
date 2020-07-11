package com.game.tictactoe;

import com.game.pattern.BottomLeftToTopRightPattern;
import com.game.pattern.HorizontalPattern;
import com.game.pattern.TopLeftToBottomRightPattern;
import com.game.pattern.VerticalPattern;

import java.util.ArrayList;
import java.util.List;

public class GameState implements IGameState {
    private final GameBoard gameBoard;
    private Player currentPlayer;
    private Cell lastMove;

    public GameState(int boardSize, Player startingPlayer) {
        gameBoard = new GameBoard(boardSize);
        currentPlayer = startingPlayer;
    }

    public GameState(GameBoard gameBoard, Player currentPlayer) {
        this.gameBoard = gameBoard;
        this.currentPlayer = currentPlayer;
    }

    public GameState(GameState other) {
        this.gameBoard = new GameBoard(other.gameBoard);
        this.currentPlayer = other.getCurrentPlayer();
        this.lastMove = other.lastMove;
    }

    @Override
    public List<GameState> availableStatesForNextPlayer() {
        List<GameState> availableStates = new ArrayList<>();
        for (Cell spot : gameBoard.getAvailableSpots()) {
            GameState newState = new GameState(this);
            newState.play(spot.getRow(), spot.getCol());
            newState.switchPlayer();
            availableStates.add(newState);
        }
        return availableStates;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Cell getLastMove() {
        return lastMove;
    }

    public boolean hasWin(Player player) {
        return new HorizontalPattern().matches(gameBoard, player)
                || new VerticalPattern().matches(gameBoard, player)
                || new TopLeftToBottomRightPattern().matches(gameBoard, player)
                || new BottomLeftToTopRightPattern().matches(gameBoard, player);
    }

    @Override
    public boolean isOver() {
        return hasWin(Player.O) || hasWin(Player.X) || gameBoard.getAvailableSpots().isEmpty();
    }

    public boolean play(int row, int col) {
        if (gameBoard.addSpot(row, col, currentPlayer)) {
            lastMove = new Cell(row, col);
            return true;
        }
        return false;

    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void switchPlayer() {
        currentPlayer = Player.getNextPlayer(currentPlayer);
    }
}
