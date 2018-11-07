package chenery.chive;

import java.util.Optional;
import java.util.Scanner;

/**
 * A command line app that allows the user to play as white against the computer
 *
 */
public class SinglePlayerCommandLineApp {

    private Game game = new Game();
    private Scanner keyboard = new Scanner(System.in);


    public static void main( String[] args ) {

        SinglePlayerCommandLineApp app = new SinglePlayerCommandLineApp();

        while(true) {
            app.nextTurn();
        }
    }

    private void nextTurn() {
        game.printBoard();
        System.out.println("\nEnter move (e.g. a2,a3):");
        String moveText = getKeyboard().nextLine();
        Optional<Move> moveParseResponse = MoveParser.parse(moveText);

        if (!moveParseResponse.isPresent()) {
            System.out.println("Invalid move");
            return;
        }

        MoveResponse response = getGame().move(Colour.WHITE, moveParseResponse.get());
        System.out.println("Made move: " + response.toString());
    }

    public Game getGame() {
        return game;
    }

    public Scanner getKeyboard() {
        return keyboard;
    }
}
