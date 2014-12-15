package kth.game.othello.board;

import java.util.Observable;

/**
 * The responsibility of an OthelloNode is to keep information of where it is
 * located on the board, who it is occupied by and inform observers when
 * occupied by another player.
 */
public class OthelloNode extends Observable implements Node {
	private int x;
	private int y;
	private String occupantPlayerId;
	private int value;

	/**
	 * Creates a new OthelloNode from a Node
	 *
	 * @param node
	 *            the node which the OthelloNode is based on
	 */
	public OthelloNode(OthelloNode node) {
		x = node.getXCoordinate();
		y = node.getYCoordinate();
		occupantPlayerId = node.getOccupantPlayerId();
		value = node.getValue();
	}

	/**
	 * Creates a new OthelloNode with provided coordinates and without an
	 * occupant
	 *
	 * @param x
	 *            the x-coordinate of the OthelloNode
	 * @param y
	 *            the y-coordinate of the OthelloNode
	 */
	public OthelloNode(int x, int y) {
		this.x = x;
		this.y = y;
		this.value = 1;
	}

	/**
	 * Creates a new OthelloNode with provided coordinates and occupant
	 *
	 * @param x
	 *            the x-coordinate of the OthelloNode
	 * @param y
	 *            the y-coordinate of the OthelloNode
	 * @param occupantPlayerId
	 *            the player ID of the occupant
	 */
	public OthelloNode(int x, int y, String occupantPlayerId) {
		this.x = x;
		this.y = y;
		this.occupantPlayerId = occupantPlayerId;
		this.value = 1;
	}

	@Override
	public String getId() {
		return this.x + "-" + this.y;
	}

	@Override
	public String getOccupantPlayerId() {
		return this.occupantPlayerId;
	}

	public void setOccupantPlayerId(String occupantPlayerId) {
		String previousPlayerId = this.occupantPlayerId;
		this.occupantPlayerId = occupantPlayerId;
		setChanged();
		notifyObservers(previousPlayerId);
	}

	@Override
	public int getXCoordinate() {
		return this.x;
	}

	@Override
	public int getYCoordinate() {
		return this.y;
	}

	@Override
	public boolean isMarked() {
		return this.occupantPlayerId != null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.getId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OthelloNode that = (OthelloNode) o;

		if (x != that.x)
			return false;
		if (y != that.y)
			return false;
		if (occupantPlayerId != null ? !occupantPlayerId.equals(that.occupantPlayerId) : that.occupantPlayerId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + (occupantPlayerId != null ? occupantPlayerId.hashCode() : 0);
		return result;
	}
}
