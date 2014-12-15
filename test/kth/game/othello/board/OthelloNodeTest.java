package kth.game.othello.board;

import org.junit.Test;

import java.util.Observer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OthelloNodeTest {

    @Test
    public void testGetId() throws Exception {
        Node node = new OthelloNode(5, 7);
        assertEquals("5-7", node.getId());
    }

    @Test
    public void testGetOccupantPlayerId() throws Exception {
        OthelloNode node = new OthelloNode(0, 0);
        node.setOccupantPlayerId("player:1");
        assertEquals("player:1", node.getOccupantPlayerId());
    }

    @Test
    public void testIsMarked() throws Exception {
        OthelloNode node = new OthelloNode(0, 0);
        assertEquals(false, node.isMarked());
        node.setOccupantPlayerId("player:1");
        assertEquals(true, node.isMarked());
    }

    @Test
    public void testNotify() throws Exception {
        OthelloNode node = new OthelloNode(0, 0);
        Observer observer = mock(Observer.class);
        node.addObserver(observer);

        node.setOccupantPlayerId("player-id");

        verify(observer).update(node, null);
        assertEquals(node.getOccupantPlayerId(), "player-id");
    }

    @Test
    public void testNotifyFromOccupied() throws Exception {
        OthelloNode node = new OthelloNode(0, 0);
        node.setOccupantPlayerId("player-1");
        Observer observer = mock(Observer.class);
        node.addObserver(observer);

        node.setOccupantPlayerId("player-2");

        verify(observer).update(node, "player-1");
        assertEquals(node.getOccupantPlayerId(), "player-2");
    }
}