package com.game.ai;

import com.game.tictactoe.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ComputerPlayer implements IComputerPlayer {

    private final GameEvaluator evaluator;
    private GameLevel gameLevel = GameLevel.EXPERT;

    public ComputerPlayer(GameEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void setGameLevel(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    @Override
    public GameState evaluateAIMove(GameState currentState) {
        if (gameLevel == GameLevel.BEGINNER) {
            return takeBlindMove(currentState);
        } else if (gameLevel == GameLevel.INTERMEDIATE) {
            return takeANoviceMove(currentState);
        }
        return takeAMasterMove(currentState);
    }

    private GameState takeBlindMove(GameState currentState) {
        List<GameState> gameStates = currentState.availableStatesForNextPlayer();
        int index = (int) (Math.random() * (gameStates.size() - 1));
        return gameStates.get(index);
    }

    /*
     * take optimal action 50% of the time, and take 1st suboptimal action 50% of the time
     */
    private GameState takeANoviceMove(GameState currentState) {
        List<GameState> miniMaxGameGameStates = getMiniMaxGameGameStatesInDescendingOrder(currentState);
        GameState chosenAction;
        double randomVal = Math.random() * 100;
        if (randomVal <= 50) {
            chosenAction = miniMaxGameGameStates.get(0);
        } else {
            if (miniMaxGameGameStates.size() >= 2) {
                //if there are two or more available actions, choose the 1st suboptimal
                chosenAction = miniMaxGameGameStates.get(1);
            } else {
                //choose the only available actions
                chosenAction = miniMaxGameGameStates.get(0);
            }
        }
        return chosenAction;
    }

    private GameState takeAMasterMove(GameState currentState) {
        List<GameState> miniMaxGameGameStates = getMiniMaxGameGameStatesInDescendingOrder(currentState);
        if (miniMaxGameGameStates.size() == 0) return null;
        return miniMaxGameGameStates.get(0);
    }

    private List<GameState> getMiniMaxGameGameStatesInDescendingOrder(GameState currentState) {
        int maximumDepthForOptimalTime = 10;
        Node root = minimax(currentState, true, Integer.MIN_VALUE, Integer.MAX_VALUE, maximumDepthForOptimalTime);
        Comparator<Node> compareById = Comparator.comparingInt(Node::getBestScore).reversed();
        root.children.sort(compareById);
        return root.children.stream().map(child -> child.state).collect(Collectors.toList());
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

        public int getBestScore() {
            return bestScore;
        }
    }
}
