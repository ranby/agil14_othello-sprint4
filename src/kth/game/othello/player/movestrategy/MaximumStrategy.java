package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import static java.util.Comparator.comparingInt;

public class MaximumStrategy extends BaseStrategy implements MoveStrategy {
    @Override
    public String getName() {
        return "maximum";
    }

    @Override
    public Node move(String playerId, Rules rules, Board board) {
        return streamAvailable(playerId, board, rules)
                .max(comparingInt((node) -> rules.getNodesToSwap(playerId, node.getId()).size()))
                .orElseThrow(IllegalStateException::new);
    }
}
