package chenery.chive.pieces;

import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
import chenery.chive.Piece;
import chenery.chive.Square;

import java.util.Set;

import static chenery.chive.Board.BLACK_QUEEN_SQUARE;
import static chenery.chive.Board.WHITE_QUEEN_SQUARE;
import static chenery.chive.Config.CAPTURE_QUEEN_VALUE;

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

    public static Queen white() {
        return new Queen(Colour.WHITE, WHITE_QUEEN_SQUARE);
    }

    public static Queen blackAt(Square at) {
        return new Queen(Colour.BLACK, at);
    }

    public static Queen black() {
        return new Queen(Colour.BLACK, BLACK_QUEEN_SQUARE);
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
    public int getPieceValue() {
        return CAPTURE_QUEEN_VALUE;
    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2655" : "\u265B";
    }
}
