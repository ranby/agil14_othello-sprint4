package kth.game.othello.tournament.match;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;

import java.util.List;


public class MatchFactory {

    public static Match createPlayableMatch(OthelloFactory othelloFactory, MoveStrategy startingStrategy, MoveStrategy secondStrategy) {
        return new PlayableMatch(createOthello(othelloFactory, startingStrategy, secondStrategy));
    }

    public static Match createViewableMatch(OthelloFactory othelloFactory, MoveStrategy startingStrategy, MoveStrategy secondStrategy) {
        return new ViewableMatch(createOthello(othelloFactory, startingStrategy, secondStrategy));
    }

    private static Othello createOthello(OthelloFactory othelloFactory, MoveStrategy startingStrategy, MoveStrategy secondStrategy) {
        Othello othello = othelloFactory.createComputerGame();
        List<Player> players = othello.getPlayers();

        players.get(0).setMoveStrategy(startingStrategy);
        players.get(1).setMoveStrategy(secondStrategy);

        othello.start(players.get(0).getId());
        return othello;
    }

}
