package chenery.chive.pieces;

import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
import chenery.chive.Piece;
import chenery.chive.Square;

import java.util.Set;

import static chenery.chive.Config.CAPTURE_ROOK_VALUE;

/**
 *
 */
public class Rook extends Piece {

    public Rook(Colour colour, Square originalLocation) {
        super(colour, originalLocation);
    }

    public static Rook whiteAt(Square at) {
        return new Rook(Colour.WHITE, at);
    }

    public static Rook blackAt(Square at) {
        return new Rook(Colour.BLACK, at);
    }

    @Override
    public Set<Move> potentialMoves() {
        return new MovesBuilder(getColour(), getCurrentLocation())
                .forward()
                .backward()
                .left()
                .right()
                .getMoves();
    }

    @Override
    public int getPieceValue() {
        return CAPTURE_ROOK_VALUE;
    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2656" : "\u265C";
    }
}
