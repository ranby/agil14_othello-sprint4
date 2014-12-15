package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class HumanPlayerTest {
    @Test
    public void testGetType() throws Exception {
        Player player = new HumanPlayer(null, null);
        assertEquals(Player.Type.HUMAN, player.getType());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetMoveStrategy() throws Exception {
        new HumanPlayer(null, null).getMoveStrategy();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetMoveStrategy() throws Exception {
        new HumanPlayer(null, null).setMoveStrategy(mock(MoveStrategy.class));
    }
}