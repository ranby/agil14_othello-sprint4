package kth.game.othello.rules;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kth.game.othello.Tests.getStandardMockedBoard;
import static kth.game.othello.Tests.getTwoMockedPlayers;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OthelloRulesTest {

    @Test
    public void testHasValidMove() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        OthelloBoard mockedBoard = getStandardMockedBoard(players);
        OthelloRules rules = new OthelloRules(mockedBoard);

        // When the game just has started, both the players should have valid moves
        assertEquals(true, rules.hasValidMove(players.get(0).getId()));
        assertEquals(true, rules.hasValidMove(players.get(1).getId()));
    }

    @Test
    public void testIsMoveValid() throws Exception {
        List<Player> mockedPlayers = getTwoMockedPlayers();
        OthelloBoard mockedBoard = getStandardMockedBoard(mockedPlayers);
        Player mock1 = mockedPlayers.get(0);

        OthelloRules rules = new OthelloRules(mockedBoard);
        assertEquals(false, rules.isMoveValid(mock1.getId(), "0-0")); // Away other nodes
        assertEquals(false, rules.isMoveValid(mock1.getId(), "4-4")); // On already placed node
        assertEquals(true, rules.isMoveValid(mock1.getId(), "3-2")); // Will swap node.
        assertEquals(true, rules.isMoveValid(mock1.getId(), "2-3")); // Will swap node.
        assertEquals(true, rules.isMoveValid(mock1.getId(), "5-4")); // Will swap node.
        assertEquals(true, rules.isMoveValid(mock1.getId(), "4-5")); // Traps player2s node
        assertEquals(false, rules.isMoveValid(mock1.getId(), "2-5")); // Next to one node
    }

    @Test
    public void testGetNodesToSwapStartBoardPlayer1TopDown() throws Exception {
        List<Player> mockedPlayers = getTwoMockedPlayers();
        OthelloBoard mockedBoard = getStandardMockedBoard(mockedPlayers);
        Player mock1 = mockedPlayers.get(0);

        OthelloRules rules = new OthelloRules(mockedBoard);

        List<Node> result = rules.getNodesToSwap(mock1.getId(), "3-2");
        assertEquals(1, result.size());
        assertEquals(mockedBoard.getNode("3-3"), result.get(0));
    }

    @Test
    public void testGetNodesToSwapStartBoardPlayer2TopDown() throws Exception {
        List<Player> mockedPlayers = getTwoMockedPlayers();
        OthelloBoard mockedBoard = getStandardMockedBoard(mockedPlayers);
        Player mock2 = mockedPlayers.get(1);

        OthelloRules rules = new OthelloRules(mockedBoard);

        List<Node> result = rules.getNodesToSwap(mock2.getId(), "3-2");
        assertEquals(0, result.size());
    }

    @Test
    public void testGetNodesToSwapStartBoardPlayer2RightLeft() throws Exception {
        List<Player> mockedPlayers = getTwoMockedPlayers();
        OthelloBoard mockedBoard = getStandardMockedBoard(mockedPlayers);
        Player mock1 = mockedPlayers.get(0);

        OthelloRules rules = new OthelloRules(mockedBoard);

        List<Node> result = rules.getNodesToSwap(mock1.getId(), "2-3");
        assertEquals(1, result.size());
        assertEquals(mockedBoard.getNode("3-3"), result.get(0));

    }

    @Test
    public void testGetNodesToSwapStartBoardPlayer1RightLeft() throws Exception {
        List<Player> mockedPlayers = getTwoMockedPlayers();
        OthelloBoard mockedBoard = getStandardMockedBoard(mockedPlayers);
        Player mock2 = mockedPlayers.get(1);

        OthelloRules rules = new OthelloRules(mockedBoard);

        List<Node> result = rules.getNodesToSwap(mock2.getId(), "2-3");
        assertEquals(0, result.size());
    }

    @Test
    public void testGetNodesToSwapSmall() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        OthelloBoard board = getStandardMockedBoard(players);
        OthelloRules rules = new OthelloRules(board);

        List<Node> nodes = new ArrayList<>();
        nodes.add(new OthelloNode(1, 0));
        nodes.add(new OthelloNode(2, 0));
        List<Node> result = rules.getNodesToSwap(nodes);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetNodesToSwapSimple() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        OthelloBoard board = getStandardMockedBoard(players);
        OthelloRules rules = new OthelloRules(board);

        List<Node> nodes = new ArrayList<>();
        nodes.add(new OthelloNode(1, 0, player1.getId()));
        Node testNode = new OthelloNode(2, 0, player2.getId());
        nodes.add(testNode);
        nodes.add(new OthelloNode(3, 0, player1.getId()));
        List<Node> result = rules.getNodesToSwap(nodes);

        assertEquals(1, result.size());
        assertEquals(testNode, result.get(0));
    }

    @Test
    public void testGetNodesToSwapLong() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        OthelloBoard board = getStandardMockedBoard(players);
        OthelloRules rules = new OthelloRules(board);

        List<Node> nodes = new ArrayList<>();
        nodes.add(new OthelloNode(1, 0, player1.getId()));
        nodes.add(new OthelloNode(2, 0, player2.getId()));
        nodes.add(new OthelloNode(3, 0, player2.getId()));
        nodes.add(new OthelloNode(4, 0, player2.getId()));
        nodes.add(new OthelloNode(5, 0, player2.getId()));
        nodes.add(new OthelloNode(6, 0, player1.getId()));
        List<Node> result = rules.getNodesToSwap(nodes);
        assertEquals(4, result.size());
    }

    @Test
    public void testGetNodesToSwapSameStart() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        OthelloBoard board = getStandardMockedBoard(players);
        OthelloRules rules = new OthelloRules(board);

        List<Node> nodes = new ArrayList<>();

        nodes.add(new OthelloNode(3, 0, player1.getId()));
        nodes.add(new OthelloNode(4, 0, player2.getId()));
        nodes.add(new OthelloNode(5, 0, player2.getId()));
        nodes.add(new OthelloNode(6, 0, player1.getId()));
        List<Node> result = rules.getNodesToSwap(nodes);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetNodesToSwapAllSame() throws Exception {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        List<Player> players = Arrays.asList(player1, player2);

        OthelloBoard board = getStandardMockedBoard(players);
        OthelloRules rules = new OthelloRules(board);

        List<Node> nodes = new ArrayList<>();
        nodes.add(new OthelloNode(1, 0, player1.getId()));
        nodes.add(new OthelloNode(2, 0, player1.getId()));
        nodes.add(new OthelloNode(3, 0, player1.getId()));
        nodes.add(new OthelloNode(4, 0, player1.getId()));
        List<Node> result = rules.getNodesToSwap(nodes);
        assertEquals(0, result.size());
    }

}
