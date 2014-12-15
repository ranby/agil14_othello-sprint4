package kth.game.othello;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.movestrategy.MaximumStrategy;
import kth.game.othello.player.movestrategy.MinimumStrategy;
import kth.game.othello.player.movestrategy.RandomStrategy;
import org.junit.Assert;
import org.junit.Test;

import kth.game.othello.board.factory.Diamond;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.movestrategy.MoveStrategy;


public class OthelloLab2IT {

	private MoveStrategy getNewMoveStrategy() {
		return new MinimumStrategy();
	}

	private OthelloFactory getOthelloFactory() {
		return new StandardOthelloFactory();
	}

    private Player createComputer(String name) { return new ComputerPlayer(name, name); }

    private Player createHuman(String name) { return new HumanPlayer(name, name); }

	private void makeNumberOfComputerMoves(int numberOfMoves, Othello othello) {
		for (int i = 0; i < numberOfMoves; i++) {
			if (!othello.isActive()) break;
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}
	}

	@Test
	public void studyTheInitialScoreTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(0).getId();
		othello.start();
		Assert.assertEquals(2, othello.getScore().getPoints(playerId));
	}

	@Test
	public void studyTheScoreAfterAMoveTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(1).getId();
		othello.start(playerId);
		othello.move(playerId, othello.getBoard().getNode(5, 3).getId());
		Assert.assertEquals(4, othello.getScore().getPoints(playerId));
	}

	@Test
	public void threeComputersOnADiamondBoardTest() {
		List<Player> players = new ArrayList<Player>();
		players.add(createComputer("black"));
		players.add(createComputer("white"));
		players.add(createComputer("orange"));
        Diamond diamond = new Diamond();
		Othello othello = getOthelloFactory().createGame(diamond.getNodes(11, players), players);
		othello.start();
		while (othello.isActive()) {
			othello.move();
		}
		Assert.assertFalse(othello.isActive());
	}

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());

		// Make some moves
		makeNumberOfComputerMoves(10, othello);

		// Change one of the computers strategy
		othello.getPlayers().get(0).setMoveStrategy(getNewMoveStrategy());

		// Make some moves
		makeNumberOfComputerMoves(1000, othello);

		Assert.assertFalse(othello.isActive());
	}
}
