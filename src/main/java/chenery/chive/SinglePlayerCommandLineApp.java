package chenery.chive;

import java.util.Scanner;

/**
 * A command line app that allows the user to play as white against the computer
 *
 */
public class SinglePlayerCommandLineApp
{



    public static void main( String[] args ) {

        Game game = new Game();

        while(true) {
            game.printBoard();

            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter move:");

            String moveText = keyboard.nextLine();

            MoveResponse response = game.move(Colour.WHITE, MoveParser.parse(moveText));

            System.out.println("Made move: " + response.toString());
        }


    }
}
