package chenery.chive.pieces;

import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
import chenery.chive.Piece;
import chenery.chive.Square;

import java.util.Set;

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
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2656" : "\u265C";
    }
}
