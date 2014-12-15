package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.Player;
import kth.game.othello.rules.OthelloRules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Tests {
    public static final int BOARD_SIZE = 8;

    public static List<OthelloNode> getStandardGameNodes(List<Player> players) {
        int origin = (BOARD_SIZE / 2 - 1);
        List<OthelloNode> nodes = new ArrayList<>();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                OthelloNode node = new OthelloNode(x, y);
                nodes.add(node);
                if (node.getYCoordinate() == origin && node.getXCoordinate() == origin) {
                    node.setOccupantPlayerId(players.get(1).getId());
                }
                if (node.getYCoordinate() == origin && node.getXCoordinate() == origin + 1) {
                    node.setOccupantPlayerId(players.get(0).getId());
                }
                if (node.getYCoordinate() == origin + 1 && node.getXCoordinate() == origin) {
                    node.setOccupantPlayerId(players.get(0).getId());
                }
                if (node.getYCoordinate() == origin + 1 && node.getXCoordinate() == origin + 1) {
                    node.setOccupantPlayerId(players.get(1).getId());
                }
            }
        }
        return nodes;
    }

    public static List<Node> getOthelloNodesAsNodes(List<OthelloNode> nodes) {
        return nodes.stream().collect(toList());
    }

    private static OthelloBoard getMockedBoard(OthelloBoard board, List<Node> nodes) {
        when(board.getNodes()).thenReturn(nodes);
        when(board.getNode(anyInt(), anyInt())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            int x = (int)args[0];
            int y = (int)args[1];

            return getNode(nodes, x, y);
        });
        when(board.getNode(anyString())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            String nodeId = (String)args[0];

            return getNode(nodes, nodeId);
        });
        when(board.hasNode(anyInt(), anyInt())).thenAnswer(invocation ->{
            Object[] args = invocation.getArguments();
            int x = (int)args[0];
            int y = (int)args[1];

            return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
        });
        return board;
    }

    public static OthelloBoard getStandardMockedBoard(List<Player> players) {
        OthelloBoard board = mock(OthelloBoard.class);

        List<OthelloNode> othelloNodes = getStandardGameNodes(players);
        List<Node> nodes = getOthelloNodesAsNodes(othelloNodes);

        return getMockedBoard(board, nodes);
    }

    public static OthelloRules getStandardMockedRules(OthelloBoard mockedBoard) {
        OthelloRules rules = mock(OthelloRules.class);
        return null;

    }

    public static Node getNode(List<Node> nodes, int x, int y) {
        for (Node node : nodes) {
            if (node.getXCoordinate() == x && node.getYCoordinate() == y) {
                return node;
            }
        }
        throw new IllegalArgumentException("No node with coordinates: " + x + ", " + y);
    }

    public static Node getNode(List<Node> nodes, String nodeId) {
        for (Node node : nodes) {
            if (node.getId().equals(nodeId)) {
                return node;
            }
        }
        throw new IllegalArgumentException("No node with id: " + nodeId);
    }

    public static List<Player> getTwoMockedPlayers() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(player1.getId()).thenReturn("id:player:1");
        when(player2.getId()).thenReturn("id:player:2");
        return Arrays.asList(player1, player2);
    }


    private Tests() {}
}
