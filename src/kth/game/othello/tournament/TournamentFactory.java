package kth.game.othello.tournament;

import kth.game.othello.OthelloFactory;
import kth.game.othello.StandardOthelloFactory;
import kth.game.othello.player.movestrategy.*;
import kth.game.othello.tournament.match.Match;
import kth.game.othello.tournament.match.MatchFactory;

import java.util.ArrayList;
import java.util.List;

public class TournamentFactory {
    public Tournament create() {
        OthelloFactory othelloFactory = new StandardOthelloFactory();
        List<Match> matches = new ArrayList<>();
        List<MoveStrategy> strategies = getStrategies();

        for (MoveStrategy startStrategy : strategies) {
            for (MoveStrategy secondStrategy : strategies) {
                if (startStrategy.equals(secondStrategy)) {
                    continue;
                }
                matches.add(MatchFactory.createPlayableMatch(othelloFactory, startStrategy, secondStrategy));
            }
        }

        return new Tournament(matches);
    }

    public Tournament createViewable() {
        OthelloFactory othelloFactory = new StandardOthelloFactory();
        List<Match> matches = new ArrayList<>();
        List<MoveStrategy> strategies = getStrategies();

        for (MoveStrategy startStrategy : strategies) {
            for (MoveStrategy secondStrategy : strategies) {
                if (startStrategy.equals(secondStrategy)) {
                    continue;
                }
                matches.add(MatchFactory.createViewableMatch(othelloFactory, startStrategy, secondStrategy));
            }
        }

        return new Tournament(matches);
    }

    private List<MoveStrategy> getStrategies() {
        List<MoveStrategy> strategies = new ArrayList<>();
        strategies.add(new RandomStrategy());
        strategies.add(new MaximumStrategy());
        strategies.add(new MinimumStrategy());
        strategies.add(new CornersFirstStrategy());

        return strategies;
    }
}
