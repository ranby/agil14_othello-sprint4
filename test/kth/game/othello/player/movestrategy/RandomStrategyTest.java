package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomStrategyTest {
    @Test
    public void testMove() throws Exception {
        MoveStrategy strategy = new RandomStrategy();

        Board mockedBoard = mock(Board.class);
        List<Node> nodes = new ArrayList<>();

        Node freeMockedNode = mock(Node.class);
        when(freeMockedNode.isMarked()).thenReturn(false);
        when(freeMockedNode.getId()).thenReturn("free:node");

        Node markedMockedNode = mock(Node.class);
        when(markedMockedNode.isMarked()).thenReturn(true);

        nodes.add(markedMockedNode);
        nodes.add(freeMockedNode);

        when(mockedBoard.getNodes()).thenReturn(nodes);

        Rules mockedRules = mock(Rules.class);
        when(mockedRules.isMoveValid(anyString(), any())).thenReturn(true);

        assertEquals(freeMockedNode, strategy.move("player", mockedRules, mockedBoard));
    }
}