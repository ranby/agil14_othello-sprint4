package kth.game.othello.board;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OthelloBoardTest {

    private List<OthelloNode> getStandardGameNodes() {
        List<OthelloNode> nodes = new ArrayList<>();

        // Init the nodes
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                OthelloNode node = new OthelloNode(x, y);
                nodes.add(node);
            }
        }

        return nodes;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetNode() throws Exception {
        OthelloBoard board = new OthelloBoard(getStandardGameNodes());
        OthelloNode node = new OthelloNode(0, 0);

        assertEquals(node, board.getNode(0, 0));
    }

    @Test
    public void testThrowsOnFaultyNode() throws Exception {
        OthelloBoard board = new OthelloBoard(getStandardGameNodes());
        exception.expect(IllegalArgumentException.class);
        board.getNode("dummy-node");
    }

    @Test
    public void testHasNode() throws Exception {
        OthelloBoard othello = new OthelloBoard(getStandardGameNodes());

        assertEquals(true, othello.hasNode(0, 0));
        assertEquals(true, othello.hasNode(0, othello.getMaxY() - 1));
        assertEquals(true, othello.hasNode(othello.getMaxX() - 1, 0));
        assertEquals(true, othello.hasNode(othello.getMaxX() - 1, othello.getMaxY() - 1));
        assertEquals(false, othello.hasNode(-1, 0));
        assertEquals(false, othello.hasNode(0, -1));
        assertEquals(false, othello.hasNode(othello.getMaxX(), 0));
        assertEquals(false, othello.hasNode(0, othello.getMaxY()));
    }

    @Test
    public void testSortNodes() throws Exception {
        List<OthelloNode> nodes = new ArrayList<>();
        nodes.add(new OthelloNode(1, 1));
        nodes.add(new OthelloNode(0, 2));
        nodes.add(new OthelloNode(1, 0));
        nodes.add(new OthelloNode(0, 0));

        OthelloBoard board = new OthelloBoard(nodes);

        List<Node> expected = new ArrayList<>();
        expected.add(new OthelloNode(0, 0));
        expected.add(new OthelloNode(0, 1));
        expected.add(new OthelloNode(0, 2));
        expected.add(new OthelloNode(1, 0));
        expected.add(new OthelloNode(1, 1));
        expected.add(new OthelloNode(1, 2));

        assertEquals(expected, board.getNodes());
    }

    @Test
    public void testInitializationOfNodes() throws Exception {
        List<OthelloNode> nodes = getStandardGameNodes();
        OthelloBoard board = new OthelloBoard(nodes);

        assertEquals(board.getMaxX() * board.getMaxY(), nodes.size());
    }
}
