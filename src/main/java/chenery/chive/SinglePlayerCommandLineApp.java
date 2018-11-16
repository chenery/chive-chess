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
            app.executeRound();
        }
    }

    private void executeRound() {
        if (game.getNextToMove() == Colour.WHITE) {
            whitePlayerTurn();
        } else {
            computerPlayerTurn();
        }
    }

    private void whitePlayerTurn() {
        game.printBoard();
        System.out.println("\nEnter move white player (e.g. a2,a3):");
        String moveText = keyboard.nextLine();
        Optional<Move> moveParseResponse = MoveParser.parse(moveText);

        if (!moveParseResponse.isPresent()) {
            System.out.println("Invalid move");
            return;
        }

        MoveResponse response = game.move(Colour.WHITE, moveParseResponse.get());
        System.out.println("White player made move: " + response.toString());
    }

    private void computerPlayerTurn() {
        MoveResponse response = game.computerToSelectMove(Colour.BLACK);
        System.out.println("Black player made move: " + response.toString());
    }


}
