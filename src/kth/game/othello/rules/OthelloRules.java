package kth.game.othello.rules;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloNode;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * The responsibility of the OthelloRules is to define when a player can make a move and in that case also
 * determine what nodes to swap at the OthelloBoard.
 */
public class OthelloRules implements Rules {
    private OthelloBoard board;

    /**
     * Creates a new OthelloRules instance using the provided
     * OthelloBoard
     *
     * @param board the OthelloBoard containing the nodes of the game
     */
    public OthelloRules(OthelloBoard board) {
        this.board = board;
    }

    @Override
    public List<Node> getNodesToSwap(String playerId, String nodeId) {
        List<Node> nodesToSwap = new ArrayList<>();
        Node currentNode = board.getNode(nodeId);
        int x = currentNode.getXCoordinate(), y = currentNode.getYCoordinate();

        // TopRight, Right, BottomRight, Bottom, BottomLeft, Left, TopLeft, Top
        int[][] directions = {{1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}};

        // Find nodes to swap in all the directions from the current node
        for (int[] direction : directions) {
            nodesToSwap.addAll(getNodesToSwap(getNodesInDirection(x, y, direction[0], direction[1], playerId)));
        }

        return nodesToSwap;
    }

    @Override
    public boolean isMoveValid(String playerId, String nodeId) {
        return !board.getNode(nodeId).isMarked() && getNodesToSwap(playerId, nodeId).size() > 0;
    }

    @Override
    public boolean hasValidMove(String playerId) {
        for (Node node : board.getNodes()) {
            if (!node.isMarked()) {
                if (isMoveValid(playerId, node.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a sequence of marked nodes following a vector from a given starting point
     *
     * @param x          the start x coordinate
     * @param y          the start y coordinate
     * @param directionX the x part of the direction vector
     * @param directionY the y part of the direction vector
     * @param playerId   the id of the first node in the sequence
     * @return a sequence of marked nodes following a vector from a given starting point
     */
    List<Node> getNodesInDirection(int x, int y, int directionX, int directionY, String playerId) {
        List<Node> sequence = new ArrayList<>();
        // Add a fake node in the beginning of the sequence
        Node nextNode = new OthelloNode(x, y, playerId);

        while (nextNode.isMarked()) {
            sequence.add(nextNode);
            x += directionX;
            y += directionY;
            if (!board.hasNode(x, y)) break;
            nextNode = board.getNode(x, y);
        }
        return sequence;
    }

    List<Node> getNodesToSwap(List<Node> nodes) {
        List<Node> nodesToSwap = new ArrayList<>();

        if (nodes.size() < 3) return emptyList();

        String currentPlayerId = nodes.get(0).getOccupantPlayerId();
        String otherPlayerId = nodes.get(1).getOccupantPlayerId();
        nodesToSwap.add(nodes.get(1));
        for (Node node : nodes.subList(2, nodes.size())) {
            String playerId = node.getOccupantPlayerId();
            if (playerId.equals(otherPlayerId)) {
                nodesToSwap.add(node);
            } else if (playerId.equals(currentPlayerId)) {
                return nodesToSwap;
            } else {
                // Belongs to someone else or empty
                return emptyList();
            }
        }

        return emptyList();
    }
}
