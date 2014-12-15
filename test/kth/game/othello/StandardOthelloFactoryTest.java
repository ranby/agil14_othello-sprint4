package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.board.factory.Diamond;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static kth.game.othello.player.Player.Type.COMPUTER;
import static kth.game.othello.player.Player.Type.HUMAN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StandardOthelloFactoryTest {
    private StandardOthelloFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new StandardOthelloFactory();
    }

    @Test
    public void testCreateComputerGame() {
        List<Player> players = factory.createComputerGame().getPlayers();
        assertEquals(Arrays.asList(COMPUTER, COMPUTER), getPlayerTypes(players));
    }

    @Test
    public void testCreateHumanGame() {
        List<Player> players = factory.createHumanGame().getPlayers();
        assertEquals(Arrays.asList(HUMAN, HUMAN), getPlayerTypes(players));
    }

    @Test
    public void testCreateHumanVersusComputerGame() {
        List<Player> players = factory.createHumanVersusComputerGame().getPlayers();
        assertEquals(Arrays.asList(HUMAN, COMPUTER), getPlayerTypes(players));
    }

    @Test
    public void testCreateGame() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        Set<NodeData> nodeData = new HashSet<>();
        NodeData node1 = new NodeData(0, 2, "id:player:2");
        NodeData node2 = new NodeData(2, 0, "id:player:1");
        NodeData node3 = new NodeData(2, 1, "id:player:1");

        nodeData.add(node1);
        nodeData.add(node2);
        nodeData.add(node3);

        Othello othello = factory.createGame(nodeData, players);

        for (NodeData data : nodeData) {
            boolean match = false;
            for (Node node : othello.getBoard().getNodes()) {
                if (data.getXCoordinate() == node.getXCoordinate() && data.getYCoordinate() == node.getYCoordinate() && data.getOccupantPlayerId() == node.getOccupantPlayerId()) {
                    match = true;
                }
            }
            assertEquals(true, match);
        }
    }

    private List<Player.Type> getPlayerTypes(List<Player> players) {
        return players.stream().map(Player::getType).collect(toList());
    }
}