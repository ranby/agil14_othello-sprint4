package kth.game.othello;

import static java.util.stream.Collectors.toList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.Player;
import kth.game.othello.score.ScoreItem;
import kth.game.othello.score.ScoreKeeper;

/**
 * Keeps track of the history of moves to be able to support an undo operation.
 */
public class Undoer {
	private Deque<UndoItem> history;

	public Undoer() {
		history = new ArrayDeque<>();

	}

	/**
	 * Adds a new point in history.
	 * 
	 * @param currentPlayer
	 *            the player who's turn it is
	 * @param swappedNodes
	 *            the nodes that were swapped (before the swap)
	 * @param list
	 */
	public void addHistory(Player currentPlayer, List<Node> swappedNodes, List<ScoreItem> scores) {
		List<ScoreItem> scoresCopy = new ArrayList<ScoreItem>();
		for (ScoreItem score : scores) {
			scoresCopy.add(new ScoreItem(score));
		}

		history.push(new UndoItem(currentPlayer, swappedNodes, scoresCopy));
	}

	/**
	 * Undoes the last move, changes nodes in place
	 * 
	 * @return the new current player
	 */
	public Player undo(ScoreKeeper scoreKeeper) {
		UndoItem item = history.remove();
		for (UndoItem.Entry entry : item.swappedNodes) {
			OthelloNode node = (OthelloNode) entry.node;
			node.setOccupantPlayerId(entry.originalOwner);
		}
		for (ScoreItem scoreItem : scoreKeeper.getPlayersScore()) {
			for (ScoreItem newScoreItem : item.scores) {
				if (scoreItem.getPlayerId().equals(newScoreItem.getPlayerId())) {
					scoreKeeper.setScoreItem(scoreItem.getPlayerId(), newScoreItem);
				}
			}
		}
		scoreKeeper.notifyEverything();
		return item.currentPlayer;
	}

	private static class UndoItem {
		public final Player currentPlayer;
		public final List<Entry> swappedNodes;
		public final List<ScoreItem> scores;

		public UndoItem(Player currentPlayer, List<Node> nodes, List<ScoreItem> scores) {
			this.currentPlayer = currentPlayer;
			swappedNodes = nodes.stream().map(node -> new Entry(node, node.getOccupantPlayerId())).collect(toList());
			this.scores = scores;
		}

		private static class Entry {
			public final Node node;
			public final String originalOwner;

			private Entry(Node node, String originalOwner) {
				this.node = node;
				this.originalOwner = originalOwner;
			}
		}
	}
}
