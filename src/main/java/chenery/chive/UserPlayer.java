package chenery.chive;

import java.util.Optional;
import java.util.Scanner;

/**
 *
 */
public class UserPlayer implements Player {

    private Colour colour;
    private Scanner keyboard = new Scanner(System.in);

    public UserPlayer(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    @Override
    public Move selectMove() {

        System.out.println("\nEnter move " + colour.name() + " player (e.g. a2,a3):");
        String moveText = keyboard.nextLine();
        Optional<Move> moveParseResponse = MoveParser.parse(moveText);

        if (!moveParseResponse.isPresent()) {
            System.out.println("Invalid move");
            return selectMove();
        }

        return moveParseResponse.get();
    }
}
