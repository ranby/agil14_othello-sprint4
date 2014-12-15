package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The responsibility of the OthelloBoard is to gather the nodes included in the OthelloGame.
 */
public class OthelloBoard implements Board {
    private List<Node> nodes;
    private OthelloNode[][] nodeMatrix;
    private int maxY;
    private int maxX;

    /**
     * Creates a new OthelloBoard based on the OthelloNodes provided.
     *
     * @param nodes the OthelloNodes that makes up the board.
     */
    public OthelloBoard(List<OthelloNode> nodes) {
        this.nodes = new ArrayList<>();
        maxY = nodes.stream().map(Node::getYCoordinate).max(Comparator.<Integer>naturalOrder()).get() + 1;
        maxX = nodes.stream().map(Node::getXCoordinate).max(Comparator.<Integer>naturalOrder()).get() + 1;

        nodeMatrix = new OthelloNode[maxY][maxX];

        for (OthelloNode node : nodes) {
            this.nodes.add(node);
            nodeMatrix[node.getYCoordinate()][node.getXCoordinate()] = node;
        }

        // fill in the missing nodes
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (nodeMatrix[y][x] == null) {
                    OthelloNode node = new OthelloNode(x, y);
                    this.nodes.add(node);
                    nodeMatrix[y][x] = node;
                }
            }
        }

        sortNodes();
    }

    // sorts the nodes on x coordinates first, then y coordinates
    void sortNodes() {
        nodes.sort((o1, o2) -> {
            int x1 = o1.getXCoordinate();
            int y1 = o1.getYCoordinate();

            int x2 = o2.getXCoordinate();
            int y2 = o2.getYCoordinate();

            if (x1 != x2) {
                return x1 - x2;
            } else if (y1 != y2) {
                return y1 - y2;
            } else {
                return 0;
            }
        });
    }

    @Override
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }

    @Override
    public Node getNode(int x, int y) {
        return nodeMatrix[y][x];
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean hasNode(int x, int y) {
        return x >= 0 && x < getMaxX() && y >= 0 && y < getMaxY();
    }

    public OthelloNode getOthelloNode(int x, int y) {
        return nodeMatrix[y][x];
    }

    /**
     * Returns the node on the board with the corresponding id
     *
     * @param nodeId the id of the node
     * @return the node on the board with the corresponding id
     */
    public Node getNode(String nodeId) {
        for (Node node : nodes) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        throw new IllegalArgumentException("No such node " + nodeId);
    }
}
