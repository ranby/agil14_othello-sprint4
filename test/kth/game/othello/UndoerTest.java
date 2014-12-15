package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.Player;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UndoerTest {
    @Test
    public void testUndo() throws Exception {
        Undoer undo = new Undoer();
        List<Node> nodes = Arrays.asList(
                new OthelloNode(0, 0, "p1"),
                new OthelloNode(1, 0, "p2"),
                new OthelloNode(2, 0)
        );
        List<Node> oldNodes = copyNodes(nodes);

        Player p1 = mock(Player.class);
        when(p1.getId()).thenReturn("p1");
        Player p2 = mock(Player.class);
        when(p2.getId()).thenReturn("p2");

        undo.addHistory(p1, nodes);

        ((OthelloNode) nodes.get(1)).setOccupantPlayerId("p1");
        ((OthelloNode) nodes.get(2)).setOccupantPlayerId("p1");

        assertEquals(p1, undo.undo());
        assertEquals(oldNodes, nodes);
    }

    private List<Node> copyNodes(List<Node> nodes) {
        return nodes.stream().map(OthelloNode::new).collect(toList());
    }
}