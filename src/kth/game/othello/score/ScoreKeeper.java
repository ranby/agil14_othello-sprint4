package kth.game.othello.score;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.TurnManager;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;

/**
 * Keeps track of the current score of a board per player.
 */
public class ScoreKeeper extends Observable implements Score {
	private Map<String, ScoreItem> scores = new HashMap<>();

	private Observer scoreObserver = (o, arg) -> {
		if (Node.class.isInstance(o)) {
			OthelloNode node = (OthelloNode) o;
			String previousPlayer = (String) arg;

			updateScore(node, previousPlayer);
		} else if (TurnManager.class.isInstance(o)) {
			String skippedPlayer = (String) arg;
			changeScore(skippedPlayer, -2);
			setChanged();
			ArrayList<String> modified = new ArrayList<>();
			modified.add(skippedPlayer);
			notifyObservers(modified);
		}

	};

	/**
	 * Construct a new score keeper with 0 scores for the passed players.
	 */
	public ScoreKeeper(List<String> playerIds) {
		for (String id : playerIds) {
			scores.put(id, new ScoreItem(id, 0));
		}
	}

	private void updateScore(OthelloNode node, String previousPlayer) {
		String currentPlayer = node.getOccupantPlayerId();
		List<String> modified = new ArrayList<>();
		if (currentPlayer != null) {
			changeScore(currentPlayer, 1 * node.getValue());
			modified.add(currentPlayer);
		}
		if (previousPlayer != null) {
			changeScore(previousPlayer, -1 * node.getValue());
			modified.add(previousPlayer);
		}

		if (!modified.isEmpty()) {
			setChanged();
			notifyObservers(modified);
		}
	}

	public void setScoreItem(String playerId, ScoreItem scoreItem) {
		scores.put(playerId, scoreItem);
	}

	private void changeScore(String playerId, int delta) {
		scores.put(playerId, new ScoreItem(playerId, scores.get(playerId).getScore() + delta));
	}

	public void notifyEverything() {
		List<String> allPlayerIds = new ArrayList<>();
		for (String playerId : scores.keySet()) {
			allPlayerIds.add(playerId);
		}
		setChanged();
		notifyObservers(allPlayerIds);
	}

	/**
	 * A list of the score of all players. The list is sorted in decreasing
	 * order regarding the score.
	 *
	 * @return a map where the keys are the id of the players and the values are
	 *         the score for that player.
	 */
	@Override
	public List<ScoreItem> getPlayersScore() {
		List<ScoreItem> items = new ArrayList<>(scores.values());
		items.sort(comparingInt(ScoreItem::getScore).reversed());
		return items;
	}

	/**
	 * Get the score of a specific player
	 *
	 * @param playerId
	 *            the id of the player
	 * @return the score
	 */
	@Override
	public int getPoints(String playerId) {
		return scores.get(playerId).getScore();
	}

	public Observer getScoreObserver() {
		return scoreObserver;
	}

	public void toPrint() {

		for (String player : scores.keySet()) {
			ScoreItem score = scores.get(player);
			System.out.println(player + ": " + score.getScore());
		}
	}
}
