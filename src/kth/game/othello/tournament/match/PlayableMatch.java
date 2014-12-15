package kth.game.othello.tournament.match;

import kth.game.othello.Othello;

public class PlayableMatch extends Match {

    public PlayableMatch(Othello othello) {
        super(othello);
    }

    @Override
    public void play() {
        while (othello.isActive()) {
            othello.move();
        }
    }
}
