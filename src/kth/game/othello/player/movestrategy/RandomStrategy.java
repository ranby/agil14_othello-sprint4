package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class RandomStrategy extends BaseStrategy implements MoveStrategy {
    private Random randomGenerator = new Random();

    @Override
    public String getName() {
        return "random";
    }

    @Override
    public Node move(String playerId, Rules rules, Board board) {
        List<Node> availableNodes = streamAvailable(playerId, board, rules).collect(toList());

        int index = randomGenerator.nextInt(availableNodes.size());
        return availableNodes.get(index);
    }
}
