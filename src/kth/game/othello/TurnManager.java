package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kth.game.othello.player.Player;
import kth.game.othello.rules.Rules;

public class TurnManager extends Observable {
	private final List<Player> players;
	private final Rules rules;
	private int currentPlayer;

	public TurnManager(Rules rules, List<Player> players, Player currentPlayer) {
		this.players = players;
		this.rules = rules;

		this.currentPlayer = players.indexOf(currentPlayer);
	}

	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns the current player or null if the game is over
	 */
	public Player getCurrentPlayer() {
		if (currentPlayer == -1)
			return null;
		return players.get(currentPlayer);
	}

	/**
	 * Moves and returns the next player or null if game is over
	 */
	public Player nextPlayer() {
		int lastPlayer = currentPlayer;
		ArrayList<String> skippedPlayers = new ArrayList<String>();

		while (true) {
			currentPlayer = (currentPlayer + 1) % players.size();
			String currentPlayerId = players.get(currentPlayer).getId();
			if (rules.hasValidMove(currentPlayerId)) {
				break;
			} else if (currentPlayer == lastPlayer) {
				currentPlayer = -1;
				return null;
			} else {
				skippedPlayers.add(currentPlayerId);
			}
		}
		if (skippedPlayers.size() > 0) {
			for (String id : skippedPlayers) {
				setChanged();
				notifyObservers(id);
			}

		}
		return getCurrentPlayer();
	}
}
