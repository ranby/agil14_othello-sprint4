package kth.game.othello.tournament.match;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;

public abstract class Match {

    protected final Othello othello;

    public Match(Othello othello) {
        this.othello = othello;
    }

    /**
     * Plays the match until one of the players win.
     */
    public abstract void play();

    /**
     * Returns the winner of the game.
     * @return the winner of the game.
     */
    public Player getWinner() {
        if (othello.isActive())
            throw new IllegalStateException("Game is still active");
        return getPlayerByScoreRank(0);
    }

    /**
     * Returns the loser of the game.
     * @return the loser of the game.
     */
    public Player getLoser() {
        if (othello.isActive())
            throw new IllegalStateException("Game is still active");
        return getPlayerByScoreRank(1);
    }

    private Player getPlayerByScoreRank(int rank) {
        String playerId = othello.getScore().getPlayersScore().get(rank).getPlayerId();
        return othello.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst().get();
    }
}