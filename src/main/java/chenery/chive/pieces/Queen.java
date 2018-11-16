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
public class Queen extends Piece {

    public Queen(Colour colour, Square originalLocation) {
        super(colour, originalLocation);
    }

    public static Queen whiteAt(Square at) {
        return new Queen(Colour.WHITE, at);
    }

    public static Queen blackAt(Square at) {
        return new Queen(Colour.BLACK, at);
    }

    @Override
    public Set<Move> potentialMoves() {
        return new MovesBuilder(getColour(), getCurrentLocation())
                .forward()
                .backward()
                .left()
                .right()
                .forwardLeftDiagonal()
                .forwardRightDiagonal()
                .backLeftDiagonal()
                .backRightDiagonal()
                .getMoves();
    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2655" : "\u265B";
    }
}
