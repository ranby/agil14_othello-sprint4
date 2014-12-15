package kth.game.othello.tournament;

import kth.game.othello.player.movestrategy.MoveStrategy;

public class Standing {
    private MoveStrategy strategy;
    private int wins;

    public Standing(MoveStrategy strategy, int wins) {
        this.strategy = strategy;
        this.wins = wins;
    }

    public int getWins() {
        return wins;
    }

    public MoveStrategy getStrategy() {
        return strategy;
    }
}
