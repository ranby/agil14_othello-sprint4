package kth.game.othello.score;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;

import org.junit.Test;

public class ScoreKeeperTest {
	private final List<String> players = asList("player1", "player2", "player3");

	@Test
	public void testGetPlayersScore() {
		ScoreKeeper keeper = new ScoreKeeper(players);
		increaseScore(keeper, "player1", null);
		increaseScore(keeper, "player1", null);
		increaseScore(keeper, "player1", null);
		increaseScore(keeper, "player2", null);
		increaseScore(keeper, "player3", null);
		increaseScore(keeper, "player3", null);
		increaseScore(keeper, "player3", null);
		increaseScore(keeper, "player3", null);
		// player1 = 3, player2 = 1, player3 = 4
		List<ScoreItem> scores = keeper.getPlayersScore();
		assertEquals("player3", scores.get(0).getPlayerId());
		assertEquals(4, scores.get(0).getScore());
		assertEquals("player1", scores.get(1).getPlayerId());
		assertEquals(3, scores.get(1).getScore());
		assertEquals("player2", scores.get(2).getPlayerId());
		assertEquals(1, scores.get(2).getScore());
	}

	@Test
	public void testGetPoints() {
		ScoreKeeper keeper = new ScoreKeeper(players);
		increaseScore(keeper, "player1", null);
		increaseScore(keeper, "player1", null);
		increaseScore(keeper, "player1", null);
		assertEquals(3, keeper.getPoints("player1"));
	}

	@Test
	public void testCorrectDecrement() {
		ScoreKeeper keeper = new ScoreKeeper(players);
		increaseScore(keeper, "player1", null);
		increaseScore(keeper, "player2", null);
		assertEquals(1, keeper.getPoints("player1"));
		assertEquals(1, keeper.getPoints("player2"));
		increaseScore(keeper, "player1", "player2");
		assertEquals(2, keeper.getPoints("player1"));
		assertEquals(0, keeper.getPoints("player2"));
	}

	@Test
	public void testObserver() {
		ScoreKeeper keeper = new ScoreKeeper(players);
		increaseScore(keeper, "player1", null);
		Observer observer = mock(Observer.class);
		keeper.addObserver(observer);

		increaseScore(keeper, "player2", "player1");
		verify(observer).update(keeper, asList("player2", "player1"));
	}

	private void increaseScore(ScoreKeeper keeper, String currentPlayer, String previousPlayer) {
		OthelloNode node = mock(OthelloNode.class);
		when(node.getOccupantPlayerId()).thenReturn(currentPlayer);
		when(node.getValue()).thenReturn(1);
		keeper.getScoreObserver().update(node, previousPlayer);
	}

	// WHYYYYYYYYY?!!
	private static abstract class DummyNode extends Observable implements Node {

	}
}