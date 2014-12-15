package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.rules.Rules;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreKeeper;

public class OthelloGame implements Othello {
	private List<Player> players;
	private Rules rules;
	private TurnManager turnManager;
	private ScoreKeeper scoreKeeper;
	private Undoer undo;
	private OthelloBoard board;
	private List<Observer> moveObservers;
	private List<Observer> gameFinishedObservers;

	/**
	 * Creates a new othello game instance
	 * 
	 * @param players
	 *            the list of players that will play the game
	 * @param board
	 *            the board which the game is played upon
	 * @param rules
	 *            the rules associated with the game
	 * @param scoreKeeper
	 *            the score keeper of the game
	 * @param undo
	 *            the undo manager
	 */
	public OthelloGame(List<Player> players, OthelloBoard board, Rules rules, ScoreKeeper scoreKeeper, Undoer undo) {
		this.players = players;
		this.board = board;
		this.rules = rules;
		this.scoreKeeper = scoreKeeper;
		this.undo = undo;

		moveObservers = new ArrayList<>();
		gameFinishedObservers = new ArrayList<>();

		observeBoard();
	}

	@Override
	public void addGameFinishedObserver(Observer observer) {
		gameFinishedObservers.add(observer);
	}

	@Override
	public void addMoveObserver(Observer observer) {
		moveObservers.add(observer);
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return rules.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public Score getScore() {
		return scoreKeeper;
	}

	@Override
	public Player getPlayerInTurn() {
		return turnManager.getCurrentPlayer();
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return rules.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		for (Player player : players) {
			if (hasValidMove(player.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return rules.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> move() {
		if (turnManager.getCurrentPlayer().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("The current player is not a computer");
		}

		ComputerPlayer player = (ComputerPlayer) turnManager.getCurrentPlayer();

		MoveStrategy strategy = player.getMoveStrategy();
		Node node = strategy.move(player.getId(), rules, board);
		while (node != null && !isMoveValid(player.getId(), node.getId())) {
			node = strategy.move(player.getId(), rules, board);
		}

		if (node != null) {
			return move(player.getId(), node.getId());
		} else {
			throw new IllegalStateException("Computer returned null node");
		}
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		if (!turnManager.getCurrentPlayer().getId().equals(playerId)) {
			throw new IllegalArgumentException("Not player " + playerId + "'s turn to play");
		}

		if (!isMoveValid(playerId, nodeId)) {
			throw new IllegalArgumentException("Invalid move " + nodeId + "for player " + playerId);
		}

		// Flip the nodes
		List<Node> nodesToSwap = getNodesToSwap(playerId, nodeId);
		nodesToSwap.add(board.getNode(nodeId)); // Add the node that we would
												// like to place as well.
		Node originalNode = board.getNode(nodeId);
		nodesToSwap.add(originalNode);

		undo.addHistory(turnManager.getCurrentPlayer(), nodesToSwap);

		for (Node node : nodesToSwap) {
			OthelloNode othelloNode = (OthelloNode) node;
			othelloNode.setOccupantPlayerId(playerId);
		}

		if (turnManager.nextPlayer() == null) {
			onGameFinished();
		}

		for (Observer o : moveObservers) {
			o.update(null, nodesToSwap);
		}

		return nodesToSwap;
	}

	@Override
	public void start() {
		int index = new Random().nextInt(players.size());
		Player player = players.get(index);
		turnManager = new TurnManager(rules, players, player);
	}

	@Override
	public void start(String playerId) {
		Player player = players.stream().filter(p -> p.getId().equals(playerId)).findFirst().get();
		turnManager = new TurnManager(rules, players, player);
	}

	@Override
	public void undo() {
		Player currentPlayer = undo.undo();
		turnManager = new TurnManager(rules, players, currentPlayer);
	}

	private void observeBoard() {
		Observer scoreObserver = scoreKeeper.getScoreObserver();

		for (int y = 0; y < board.getMaxY(); y++) {
			for (int x = 0; x < board.getMaxX(); x++) {
				board.getOthelloNode(x, y).addObserver(scoreObserver);
				scoreObserver.update(board.getOthelloNode(x, y), null);
			}
		}
	}

	private void onGameFinished() {
		for (Observer o : gameFinishedObservers) {
			o.update(null, null);
		}
	}
}
