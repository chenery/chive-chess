package chenery.chive.pieces;

import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
import chenery.chive.Piece;
import chenery.chive.Square;

import java.util.Set;

import static chenery.chive.Config.CAPTURE_KNIGHT_VALUE;

/**
 *
 */
public class Knight extends Piece {

    public Knight(Colour colour, Square originalLocation) {
        super(colour, originalLocation);
    }

    public static Knight whiteAt(Square at) {
        return new Knight(Colour.WHITE, at);
    }

    public static Knight blackAt(Square at) {
        return new Knight(Colour.BLACK, at);
    }

    @Override
    public Set<Move> potentialMoves() {
        return new MovesBuilder(getColour(), getCurrentLocation())
                .move(true, 2, true, 1)
                .move(true, 1, true, 2)
                .move(true, 2, false, 1)
                .move(true, 1, false, 2)
                .move(false, 2, true, 1)
                .move(false, 1, true, 2)
                .move(false, 2, false, 1)
                .move(false, 1, false, 2)
                .getMoves();
    }

    @Override
    public int getPieceValue() {
        return CAPTURE_KNIGHT_VALUE;
    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2658" : "\u265E";
    }
}
