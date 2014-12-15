package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

public class HumanPlayer extends BasePlayer {
    public HumanPlayer(String id, String name) {
        super(id, name);
    }

    @Override
    public Type getType() {
        return Type.HUMAN;
    }

    @Override
    public MoveStrategy getMoveStrategy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMoveStrategy(MoveStrategy moveStrategy) {
        throw new UnsupportedOperationException();
    }

}
