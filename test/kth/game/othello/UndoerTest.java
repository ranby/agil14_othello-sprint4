package kth.game.othello;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;
import kth.game.othello.score.ScoreKeeper;

import org.junit.Test;

public class UndoerTest {
	@Test
	public void testUndo() throws Exception {
		Undoer undo = new Undoer();
		List<Node> nodes = Arrays.asList(new OthelloNode(0, 0, "p1"), new OthelloNode(1, 0, "p2"),
				new OthelloNode(2, 0));
		List<Node> oldNodes = copyNodes(nodes);

		Player p1 = mock(Player.class);
		when(p1.getId()).thenReturn("p1");
		Player p2 = mock(Player.class);
		when(p2.getId()).thenReturn("p2");

		ArrayList<ScoreItem> scores = new ArrayList<>();
		ScoreItem p1score = mock(ScoreItem.class);
		when(p1score.getScore()).thenReturn(10);
		when(p1score.getPlayerId()).thenReturn("p1");
		ScoreItem p2score = mock(ScoreItem.class);
		when(p2score.getScore()).thenReturn(12);
		when(p2score.getPlayerId()).thenReturn("p2");
		scores.add(p1score);
		scores.add(p2score);

		ScoreKeeper goalKeeper = mock(ScoreKeeper.class);
		when(goalKeeper.getPlayersScore()).thenReturn(scores);

		undo.addHistory(p1, nodes, scores);

		((OthelloNode) nodes.get(1)).setOccupantPlayerId("p1");
		((OthelloNode) nodes.get(2)).setOccupantPlayerId("p1");

		assertEquals(p1, undo.undo(goalKeeper));
		assertEquals(oldNodes, nodes);
	}

	private List<Node> copyNodes(List<Node> nodes) {
		return nodes.stream().map(OthelloNode::new).collect(toList());
	}
}