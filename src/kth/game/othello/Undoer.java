package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloNode;
import kth.game.othello.player.Player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Keeps track of the history of moves to be able
 * to support an undo operation.
 */
public class Undoer {
    private Deque<UndoItem> history;

    public Undoer() {
        history = new ArrayDeque<>();

    }

    /**
     * Adds a new point in history.
     * @param currentPlayer the player who's turn it is
     * @param swappedNodes the nodes that were swapped (before the swap)
     */
    public void addHistory(Player currentPlayer, List<Node> swappedNodes) {
        history.push(new UndoItem(currentPlayer, swappedNodes));
    }

    /**
     * Undoes the last move, changes nodes in place
     * @return the new current player
     */
    public Player undo() {
        UndoItem item = history.remove();
        for (UndoItem.Entry entry : item.swappedNodes) {
            OthelloNode node = (OthelloNode) entry.node;
            node.setOccupantPlayerId(entry.originalOwner);
        }
        return item.currentPlayer;
    }

    private static class UndoItem {
        public final Player currentPlayer;
        public final List<Entry> swappedNodes;

        public UndoItem(Player currentPlayer, List<Node> nodes) {
            this.currentPlayer = currentPlayer;
            swappedNodes = nodes.stream().map(node -> new Entry(node, node.getOccupantPlayerId())).collect(toList());
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
