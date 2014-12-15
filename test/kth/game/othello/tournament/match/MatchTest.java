package kth.game.othello.tournament.match;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MatchTest {

    @Test(expected = IllegalStateException.class)
    public void testGetWinnerOfActiveGame() throws Exception {
        Othello othello = mock(Othello.class);
        when(othello.isActive()).thenReturn(true);
        Match match = new Match(othello) {
            public void play() {
            }
        };

        match.getWinner();
    }

    @Test
    public void testGetWinner() {
        Othello othello = mock(Othello.class);
        Score score = mock(Score.class);
        ScoreItem scoreItem1 = mock(ScoreItem.class);
        ScoreItem scoreItem2 = mock(ScoreItem.class);
        Player winner = mock(Player.class);

        Match match = new Match(othello) {
            public void play() {
            }
        };

        when(othello.getScore()).thenReturn(score);
        when(score.getPlayersScore()).thenReturn(Arrays.asList(scoreItem1, scoreItem2));
        when(scoreItem1.getPlayerId()).thenReturn("player-1");
        when(scoreItem2.getPlayerId()).thenReturn("player-2");
        when(othello.getPlayers()).thenReturn(Arrays.asList(winner));
        when(winner.getId()).thenReturn("player-1");

        assertEquals(match.getWinner(), winner);
    }

}