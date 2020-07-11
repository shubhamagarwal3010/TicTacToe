package com.game.ai;

import com.game.tictactoe.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComputerPlayer implements IComputerPlayer {

    private final GameEvaluator evaluator;

    public ComputerPlayer(GameEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public GameState evaluateBestMove(GameState currentState) {
        Node root = minimax(currentState, true, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Node child : root.children) {
            if (child.bestScore == root.bestScore) {
                return child.state;
            }
        }
        return null;
    }

    // This is the minimax function. It considers all the possible ways the game can go and returns
    // the value of the board
    private Node minimax(GameState state, boolean isMaximizer, int maximizerBest, int minimizerBest, int depth) {
        Node current = new Node(state, maximizerBest, minimizerBest);
        current.bestScore = isMaximizer ? maximizerBest : minimizerBest;
        if (depth == 0 || state.isOver()) {
            current.bestScore = evaluator.evaluateGameScore(state);
            current.maximizerBest = current.bestScore;
            current.minimizerBest = current.bestScore;
            current.state = state;
            current.children = Collections.emptyList();
            return current;
        }
        ArrayList<Node> children = new ArrayList<>();
        for (GameState nextState : state.availableStatesForNextPlayer()) {
            Node child = minimax(nextState, !isMaximizer, current.maximizerBest, current.minimizerBest, depth - 1);
            if (isMaximizer && child.bestScore > current.bestScore) {
                current.bestScore = child.bestScore;
                current.maximizerBest = child.bestScore;
            } else if (!isMaximizer && child.bestScore < current.bestScore) {
                current.bestScore = child.bestScore;
                current.minimizerBest = child.bestScore;
            }
            // prune condition
            if (current.maximizerBest >= current.minimizerBest) {
                break;
            }
            children.add(child);
        }
        current.children = children;
        return current;
    }

    private static class Node {
        private GameState state;
        private int maximizerBest;
        private int minimizerBest;
        private int bestScore;
        private List<Node> children;

        public Node(GameState state, int maximizerBest, int minimizerBest) {
            this.maximizerBest = maximizerBest;
            this.minimizerBest = minimizerBest;
            this.state = state;
        }
    }
}
