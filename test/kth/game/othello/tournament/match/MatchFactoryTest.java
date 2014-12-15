package kth.game.othello.tournament.match;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;


public class MatchFactoryTest {

    @Test
    public void testCreatePlayableMatch() {
        OthelloFactory othelloFactory = mock(OthelloFactory.class);
        Othello othello = mock(Othello.class);
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        MoveStrategy moveStrategy1 = mock(MoveStrategy.class);
        MoveStrategy moveStrategy2 = mock(MoveStrategy.class);

        when(othelloFactory.createComputerGame()).thenReturn(othello);
        when(othello.getPlayers()).thenReturn(Arrays.asList(player1, player2));
        when(player1.getId()).thenReturn("player-1");

        MatchFactory.createPlayableMatch(othelloFactory, moveStrategy1, moveStrategy2);

        verify(player1, atLeastOnce()).setMoveStrategy(moveStrategy1);
        verify(player2, atLeastOnce()).setMoveStrategy(moveStrategy2);
        verify(othello, atLeastOnce()).start("player-1");
    }

}
