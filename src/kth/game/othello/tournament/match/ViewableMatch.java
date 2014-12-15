package kth.game.othello.tournament.match;

import kth.game.othello.Othello;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;


public class ViewableMatch extends Match {

    public ViewableMatch(Othello othello) {
        super(othello);
    }

    @Override
    public void play() {
        OthelloView othelloView = OthelloViewFactory.create(othello, 0, 500);
        othelloView.start(othello.getPlayerInTurn().getId());
    }
}
