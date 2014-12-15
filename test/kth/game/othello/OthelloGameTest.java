package kth.game.othello;

import static kth.game.othello.Tests.getTwoMockedPlayers;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.Player;
import kth.game.othello.rules.OthelloRules;
import kth.game.othello.score.ScoreKeeper;

import org.junit.Test;

public class OthelloGameTest {

	@Test
	public void testIsActiveReturnsFalseWhenNoPlayerCanMove() throws Exception {
		List<Player> players = getTwoMockedPlayers();
		OthelloBoard board = mock(OthelloBoard.class);
		OthelloRules rules = mock(OthelloRules.class);
		ScoreKeeper scoreKeeper = mock(ScoreKeeper.class);
		Undoer undo = mock(Undoer.class);
		when(rules.hasValidMove(anyString())).thenReturn(false);
		OthelloGame othello = new OthelloGame(players, board, rules, scoreKeeper, undo);

		othello.start();

		assertEquals(false, othello.isActive());
	}

	@Test
	public void testIsActiveReturnsTrueWhenAPlayerCanMove() throws Exception {
		List<Player> players = getTwoMockedPlayers();
		OthelloBoard board = mock(OthelloBoard.class);
		OthelloRules rules = mock(OthelloRules.class);
		ScoreKeeper scoreKeeper = mock(ScoreKeeper.class);
		Undoer undo = mock(Undoer.class);
		when(rules.hasValidMove(anyString())).thenReturn(true);
		OthelloGame othello = new OthelloGame(players, board, rules, scoreKeeper, undo);

		othello.start();

		assertEquals(true, othello.isActive());
	}
}