package kth.game.othello.tournament.match;

import kth.game.othello.Othello;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PlayableMatchTest {

    @Test
    public void testPlay() throws Exception {
        Othello othello = mock(Othello.class);
        PlayableMatch match = new PlayableMatch(othello);
        when(othello.isActive()).thenReturn(true, true, false);

        match.play();

        verify(othello, times(3)).isActive();
        verify(othello, times(2)).move();
    }
}
