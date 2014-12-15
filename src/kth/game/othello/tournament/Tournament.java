package kth.game.othello.tournament;

import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.tournament.match.Match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;

/**
 * Represents a tournament of matches.
 */
public class Tournament {
    // Package private for test
    List<Match> matches;

    /**
     * Construct a new tournament from a list of matches.
     */
    public Tournament(List<Match> matches) {
        this.matches = matches;
    }

    /**
     * Start the tournament, this will play each match
     */
    public void start() {
        for (Match match : matches) {
            match.play();
        }
    }

    /**
     * Returns the current standings, or the final standings if the
     * tournament if over.
     */
    public List<Standing> getStandings() {
        Map<MoveStrategy, Integer> map = new HashMap<>();
        List<Standing> standings = new ArrayList<>();
        MoveStrategy winner, loser;

        // Calculate the score of different move strategies
        for (Match match : this.matches) {
            winner = match.getWinner().getMoveStrategy();
            loser = match.getLoser().getMoveStrategy();

            if (!map.containsKey(winner)) {
                map.put(winner, 1);
            } else {
                map.put(winner, map.get(winner) + 1);
            }
            if (!map.containsKey(loser)) {
                map.put(loser, 0);
            }
        }

        // Transform the map into an list.
        for (Map.Entry<MoveStrategy, Integer> entry : map.entrySet()) {
            standings.add(new Standing(entry.getKey(), entry.getValue()));
        }

        standings.sort(comparingInt(Standing::getWins).reversed());

        return standings;
    }
}
