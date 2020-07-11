package com.game.ai;

public enum GameLevel {
    BEGINNER(1),
    INTERMEDIATE(2),
    EXPERT(3);

    private final int level;

    GameLevel(int level) {
        this.level = level;
    }

    public static GameLevel getLevel(int level) {
        switch (level) {
            case 1:
                return BEGINNER;
            case 2:
                return INTERMEDIATE;
            case 3:
                return EXPERT;
            default:
                throw new IllegalArgumentException("Not a valid game level.");
        }
    }

    @Override
    public String toString() {
        return this.name() + "(" + level + ')';
    }
}
