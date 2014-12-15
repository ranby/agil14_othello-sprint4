package kth.game.othello;

import java.util.ArrayList;

import kth.game.othello.board.factory.Square;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class Main {

	public static void main(String[] args) {
		OthelloFactory factory = new StandardOthelloFactory();
		ArrayList<Player> players = new ArrayList<>();
		players.add(new HumanPlayer("Pelle", "Pelle"));
		players.add(new HumanPlayer("Kalle", "Kalle"));
		Othello game = factory.createGame(new Square().getNodes(4, players), players);
		OthelloView othelloView = OthelloViewFactory.create(game, 0, 100);
		othelloView.start();
	}

}
