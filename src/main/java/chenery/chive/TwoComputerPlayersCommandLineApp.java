package chenery.chive;

/**
 * A command line app that allows the user to play as white against the computer
 *
 */
public class TwoComputerPlayersCommandLineApp {

    private Game game = Game.twoComputersGame();

    public static void main( String[] args ) {

        TwoComputerPlayersCommandLineApp app = new TwoComputerPlayersCommandLineApp();

        while(!app.executeMove().isGameOver());
    }

    private MoveResponse executeMove() {
        game.printBoard();
        Player player = game.getNextPlayerToMove();
        MoveResponse response = game.move(player.getColour(), player.selectMove());

        if (response.isInvalid()) {
            System.out.println(player.getColour() + " made invalid move: " + response.toString());
        }

        if (response.isGameOver()) {
            game.printBoard();
            System.out.println("Game over: " + response.toString());
            System.out.println(game.getHistory().numberOfMovesMade() + " moves made.");
        } else {
            System.out.println(player.getColour() + " player made move: " + response.toString());
        }

        return response;
    }
}
