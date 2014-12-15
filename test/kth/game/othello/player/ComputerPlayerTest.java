package kth.game.othello.player;


import kth.game.othello.player.movestrategy.MoveStrategy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ComputerPlayerTest {
    @Test
    public void testGetType() throws Exception {
        Player player = new ComputerPlayer(null, null);
        assertEquals(Player.Type.COMPUTER, player.getType());
    }

    @Test
    public void testSetStrategy() throws Exception {
        Player player = new ComputerPlayer(null, null);
        MoveStrategy strategy = mock(MoveStrategy.class);
        player.setMoveStrategy(strategy);
        assertEquals(strategy, player.getMoveStrategy());
    }
}