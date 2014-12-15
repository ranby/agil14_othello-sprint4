package kth.game.othello;

import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class OthelloPlayer {
    public static void main(String[] args) {
        Othello othello = new StandardOthelloFactory().createHumanVersusComputerGame();
        OthelloView othelloView = OthelloViewFactory.create(othello, 0, 1000);
        othelloView.start(othello.getPlayers().get(0).getId());
    }
}
