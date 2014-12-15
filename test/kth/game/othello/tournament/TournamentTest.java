package kth.game.othello.tournament;

import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.tournament.match.Match;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TournamentTest {

    @Test
    public void testStart() {
        Match match1 = mock(Match.class);
        Match match2 = mock(Match.class);
        Tournament tournament = new Tournament(Arrays.asList(match1, match2));

        tournament.start();

        verify(match1, times(1)).play();
        verify(match2, times(1)).play();
    }

    @Test
    public void testGetStandings() {
        List<MoveStrategy> strategies = new ArrayList<>();
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            strategies.add(mock(MoveStrategy.class));
            players.add(mock(Player.class));
            when(players.get(i).getMoveStrategy()).thenReturn(strategies.get(i));
        }

        Match m1 = mock(Match.class), m2 = mock(Match.class), m3 = mock(Match.class);
        when(m1.getWinner()).thenReturn(players.get(0));
        when(m1.getLoser()).thenReturn(players.get(1));
        when(m2.getWinner()).thenReturn(players.get(1));
        when(m2.getLoser()).thenReturn(players.get(0));
        when(m3.getWinner()).thenReturn(players.get(0));
        when(m3.getLoser()).thenReturn(players.get(2));

        Tournament tournament = new Tournament(Arrays.asList(m1, m2, m3));

        List<Standing> standings = tournament.getStandings();

        assertEquals(standings.get(0).getStrategy(), strategies.get(0));
        assertEquals(standings.get(0).getWins(), 2);
        assertEquals(standings.get(1).getStrategy(), strategies.get(1));
        assertEquals(standings.get(1).getWins(), 1);
        assertEquals(standings.get(2).getStrategy(), strategies.get(2));
        assertEquals(standings.get(2).getWins(), 0);
    }

}
