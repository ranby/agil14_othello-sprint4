package kth.game.othello;

import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TurnManagerTest {
    @Test
    public void testGetCurrentWithPlayer1() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Rules rules = mock(Rules.class);

        TurnManager manager = new TurnManager(rules, Arrays.asList(player1, player2), player1);

        assertEquals(player1, manager.getCurrentPlayer());
    }

    @Test
    public void testNextPlayer() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Rules rules = mock(Rules.class);
        when(rules.hasValidMove(anyString())).thenReturn(true);

        TurnManager manager = new TurnManager(rules, Arrays.asList(player1, player2, player3), player1);

        assertEquals(player2, manager.nextPlayer());
        assertEquals(player3, manager.nextPlayer());
        assertEquals(player1, manager.nextPlayer());
    }

    @Test
    public void testNoMoves() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        when(player1.getId()).thenReturn("p1");
        when(player2.getId()).thenReturn("p2");
        when(player3.getId()).thenReturn("p3");

        Rules rules = mock(Rules.class);
        when(rules.hasValidMove("p1")).thenReturn(true);
        when(rules.hasValidMove("p2")).thenReturn(false);
        when(rules.hasValidMove("p3")).thenReturn(true);

        TurnManager manager = new TurnManager(rules, Arrays.asList(player1, player2, player3), player1);

        assertEquals(player3, manager.nextPlayer());
        assertEquals(player1, manager.nextPlayer());
    }

    @Test
    public void testGameOver() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Player player3 = mock(Player.class);
        Rules rules = mock(Rules.class);
        when(rules.hasValidMove(anyString())).thenReturn(false);

        TurnManager manager = new TurnManager(rules, Arrays.asList(player1, player2, player3), player1);

        assertNull(manager.nextPlayer());
        assertNull(manager.getCurrentPlayer());
    }
}
