package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import java.util.function.ToIntFunction;

import static java.util.Comparator.comparingInt;

public class CornersFirstStrategy extends BaseStrategy implements MoveStrategy {
    @Override
    public String getName() {
        return "corners-first";
    }

    @Override
    public Node move(String playerId, Rules rules, Board board) {

        ToIntFunction<Node> score = (node) -> {
            int nodes = rules.getNodesToSwap(playerId, node.getId()).size();
            int horizontalDistance = Math.abs(node.getXCoordinate() - board.getMaxX()/2);
            int verticalDistance = Math.abs(node.getYCoordinate() - board.getMaxY()/2);
            return nodes + (horizontalDistance + verticalDistance)*5;
        };

        return streamAvailable(playerId, board, rules)
                .max(comparingInt(score))
                .orElseThrow(IllegalStateException::new);
    }

}
