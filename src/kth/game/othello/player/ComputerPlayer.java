package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MaximumStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;


public class ComputerPlayer extends BasePlayer {
    private MoveStrategy strategy;

    public ComputerPlayer(String id, String name) {
        this(id, name, new MaximumStrategy());
    }

    public ComputerPlayer(String id, String name, MoveStrategy strategy) {
        super(id, name);

        this.strategy = strategy;
    }

    @Override
    public Type getType() {
        return Type.COMPUTER;
    }

    @Override
    public void setMoveStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public MoveStrategy getMoveStrategy() {
        return strategy;
    }
}
