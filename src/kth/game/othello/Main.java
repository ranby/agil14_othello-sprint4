package kth.game.othello;

import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class Main {

	public static void main(String[] args) {
		OthelloFactory factory = new StandardOthelloFactory();
		Othello game = factory.createHumanGame();
		OthelloView othelloView = OthelloViewFactory.create(game, 0, 100);
		othelloView.start();
	}

}
