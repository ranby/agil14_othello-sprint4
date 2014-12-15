package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import java.util.stream.Stream;

public class BaseStrategy {
    protected Stream<Node> streamAvailable(String playerId, Board board, Rules rules) {
        return board.getNodes().stream()
                .filter(node -> !node.isMarked() && rules.isMoveValid(playerId, node.getId()));
    }
}
