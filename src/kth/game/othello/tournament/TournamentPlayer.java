package kth.game.othello.tournament;


public class TournamentPlayer {
    public static void main(String[] args) {
        TournamentPlayer tournamentPlayer = new TournamentPlayer();
        tournamentPlayer.play();
    }

    public void play() {
        TournamentFactory tournamentFactory = new TournamentFactory();
        Tournament tournament = tournamentFactory.create();
        start(tournament);
    }

    public void playViewAble() {
        TournamentFactory tournamentFactory = new TournamentFactory();
        Tournament tournament = tournamentFactory.createViewable();
        start(tournament);
    }

    private void start(Tournament tournament) {
        tournament.start();
        for (Standing s : tournament.getStandings()) {;
            System.out.println(s.getStrategy().getName() + ", " + s.getWins());
        }

    }
}
