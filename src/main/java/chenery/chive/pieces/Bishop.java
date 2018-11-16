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
public class Bishop extends Piece {

    public Bishop(Colour colour, Square originalLocation) {
        super(colour, originalLocation);
    }

    public static Bishop whiteAt(Square at) {
        return new Bishop(Colour.WHITE, at);
    }

    public static Bishop blackAt(Square at) {
        return new Bishop(Colour.BLACK, at);
    }

    @Override
    public Set<Move> potentialMoves() {
        return new MovesBuilder(getColour(), getCurrentLocation())
                .forwardLeftDiagonal()
                .forwardRightDiagonal()
                .backLeftDiagonal()
                .backRightDiagonal()
                .getMoves();
    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2657" : "\u265D";
    }
}
