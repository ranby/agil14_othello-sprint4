package kth.game.othello.tournament;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TournamentFactoryTest {

    @Test
    public void testCreate() {
        TournamentFactory tournamentFactory = new TournamentFactory();

        Tournament tournament = tournamentFactory.create();

        assertEquals(tournament.matches.size(), 12);

    }
}
