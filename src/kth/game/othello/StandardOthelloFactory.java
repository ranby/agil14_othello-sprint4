package kth.game.othello;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.board.factory.Diamond;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.rules.OthelloRules;
import kth.game.othello.score.ScoreKeeper;

public class StandardOthelloFactory implements OthelloFactory {
	@Override
	public Othello createComputerGame() {
		Player player1 = new ComputerPlayer("computer1", "Computer 1");
		Player player2 = new ComputerPlayer("computer2", "Computer 2");
		return createGame(player1, player2);
	}

	@Override
	public Othello createHumanGame() {
		Player player1 = new HumanPlayer("human1", "Human 1");
		Player player2 = new HumanPlayer("human2", "Human 2");
		return createGame(player1, player2);

	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Player player1 = new HumanPlayer("human", "Human");
		Player player2 = new ComputerPlayer("computer", "Human");
		return createGame(player1, player2);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		List<OthelloNode> nodes = new ArrayList<>();

		for (NodeData n : nodesData) {
			OthelloNode on = new OthelloNode(n.getXCoordinate(), n.getYCoordinate(), n.getOccupantPlayerId());
			nodes.add(on);
		}

		OthelloBoard board = new OthelloBoard(nodes);
		return createGame(players, board);
	}

	public Othello createDiamondGame() {
		Player player1 = new HumanPlayer("human1", "Human1");
		Player player2 = new HumanPlayer("human2", "Human2");
		Player player3 = new HumanPlayer("human3", "Human3");
		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);
		return createGame(new Diamond().getNodes(7, players), players);
	}

	private OthelloGame createGame(Player player1, Player player2) {
		List<Player> players = Arrays.asList(player1, player2);
		List<OthelloNode> nodes = getStandardGameNodes(players);
		OthelloBoard board = new OthelloBoard(nodes);
		return createGame(players, board);
	}

	private OthelloGame createGame(List<Player> players, OthelloBoard board) {
		OthelloRules rules = new OthelloRules(board);
		ScoreKeeper scoreKeeper = new ScoreKeeper(players.stream().map(Player::getId).collect(toList()));
		Undoer undo = new Undoer();

		return new OthelloGame(players, board, rules, scoreKeeper, undo);
	}

	private List<OthelloNode> getStandardGameNodes(List<Player> players) {
		List<OthelloNode> nodes = new ArrayList<>();
		Set<NodeData> nodesData = new Square().getNodes(8, players);

		for (NodeData n : nodesData) {
			OthelloNode on = new OthelloNode(n.getXCoordinate(), n.getYCoordinate(), n.getOccupantPlayerId());
			nodes.add(on);
		}

		return nodes;
	}
}
