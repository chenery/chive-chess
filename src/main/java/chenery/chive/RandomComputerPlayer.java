package chenery.chive;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  The simplest computerised logic possible:
 *
 *      - review all pieces for the colour, and all their possible moves
 *      - randomly select one of the possible moves
 */
public class RandomComputerPlayer implements Player {

    private Colour colour;
    private Board board;

    public RandomComputerPlayer(Colour colour, Board board) {
        this.colour = colour;
        this.board = board;
    }

    @Override
    public Colour getColour() {
        return colour;
    }

    @Override
    public Move selectMove() {
        List<Move> possibleMoves = new ArrayList<>(MoveValidator.validMoves(colour, board));

        if (possibleMoves.size() == 0) {
            // todo handle the end game state
            throw new IllegalStateException("Cannot find move for player");
        }

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomMoveIndex = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
        return possibleMoves.get(randomMoveIndex);
    }
}
