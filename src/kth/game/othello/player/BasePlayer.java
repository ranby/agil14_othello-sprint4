package kth.game.othello.player;

abstract class BasePlayer implements Player {
	private final String id;
	private final String name;

	protected BasePlayer(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
