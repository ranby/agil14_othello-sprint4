package kth.game.othello.score;

import kth.game.othello.board.Node;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;

/**
 * Keeps track of the current score of a board per player.
 */
public class ScoreKeeper extends Observable implements Score {
    private Map<String, ScoreItem> scores = new HashMap<>();

    private Observer scoreObserver = (o, arg) -> {
        Node node = (Node) o;
        String previousPlayer = (String) arg;

        updateScore(node, previousPlayer);
    };

    /**
     * Construct a new score keeper with 0 scores for the
     * passed players.
     */
    public ScoreKeeper(List<String> playerIds) {
        for (String id : playerIds) {
            scores.put(id, new ScoreItem(id, 0));
        }
    }

    private void updateScore(Node node, String previousPlayer) {
        String currentPlayer = node.getOccupantPlayerId();
        List<String> modified = new ArrayList<>();
        if (currentPlayer != null) {
            changeScore(currentPlayer, 1);
            modified.add(currentPlayer);
        }
        if (previousPlayer != null) {
            changeScore(previousPlayer, -1);
            modified.add(previousPlayer);
        }

        if (!modified.isEmpty()) {
            setChanged();
            notifyObservers(modified);
        }
    }

    private void changeScore(String playerId, int delta) {
        scores.put(playerId, new ScoreItem(playerId, scores.get(playerId).getScore() + delta));
    }

    /**
     * A list of the score of all players. The list is sorted in decreasing order regarding the score.
     *
     * @return a map where the keys are the id of the players and the values are the score for that player.
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
     * @param playerId the id of the player
     * @return the score
     */
    @Override
    public int getPoints(String playerId) {
        return scores.get(playerId).getScore();
    }

    public Observer getScoreObserver() {
        return scoreObserver;
    }
}
