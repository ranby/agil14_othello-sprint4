package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import kth.game.othello.board.OthelloNode;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;

import org.junit.Test;

public class OthelloLab4IT {

	@Test
	public void skippedPlayersAndUndoTest() {

		OthelloFactory factory = new StandardOthelloFactory();
		ArrayList<Player> players = new ArrayList<>();
		players.add(new HumanPlayer("Pelle", "Pelle"));
		players.add(new HumanPlayer("Kalle", "Kalle"));
		Othello game = factory.createGame(new Square().getNodes(4, players), players);
		// Create game state
		((OthelloNode) game.getBoard().getNode(1, 1)).setOccupantPlayerId(null);
		((OthelloNode) game.getBoard().getNode(1, 2)).setOccupantPlayerId(null);
		((OthelloNode) game.getBoard().getNode(2, 1)).setOccupantPlayerId(null);
		((OthelloNode) game.getBoard().getNode(2, 2)).setOccupantPlayerId("Pelle");
		((OthelloNode) game.getBoard().getNode(1, 3)).setOccupantPlayerId("Pelle");
		((OthelloNode) game.getBoard().getNode(2, 3)).setOccupantPlayerId("Kalle");
		game.start("Kalle");
		// kalle 3 - 2 pelle
		game.move("Kalle", "0-3");
		// kalle flips and places 1 edge node = + 4 points
		assertEquals(6, game.getScore().getPoints("Kalle"));
		// Pelle gets -2 for Kalles swap and -2 for skipped
		assertEquals(-1, game.getScore().getPoints("Pelle"));
		try {
			game.move("Pelle", "1-2");
			assertEquals(1, 2); // FAILS
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		game.move("Kalle", "2-1");
		assertEquals(8, game.getScore().getPoints("Kalle"));
		assertEquals(-2, game.getScore().getPoints("Pelle"));
		game.undo();
		assertEquals(6, game.getScore().getPoints("Kalle"));
		assertEquals(-1, game.getScore().getPoints("Pelle"));
		assertEquals("Kalle", game.getPlayerInTurn().getId());
		game.undo();
		// game.undo(); <-throws exception
		// Back to start
		game.move("Kalle", "0-3");
		// kalle flips and places 1 edge node = + 4 points
		assertEquals(6, game.getScore().getPoints("Kalle"));
		// Pelle gets -2 for Kalles swap and -2 for skipped
		assertEquals(-1, game.getScore().getPoints("Pelle"));

	}

}
