package chenery.chive.pieces;

import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
import chenery.chive.Piece;
import chenery.chive.Square;

import java.util.Set;

import static chenery.chive.Board.BLACK_KING_SQUARE;
import static chenery.chive.Board.WHITE_KING_SQUARE;

/**
 *
 */
public class King extends Piece {

    public King(Colour colour, Square originalLocation) {
        super(colour, originalLocation);
    }

    public static King buildKing(Colour forColour) {
        Square kingLocation = forColour == Colour.WHITE ? WHITE_KING_SQUARE : BLACK_KING_SQUARE;
        return new King(forColour, kingLocation);
    }

    public static King whiteAt(Square at) {
        return buildKing(Colour.WHITE, at);
    }

    public static King white() {
        return buildKing(Colour.WHITE, WHITE_KING_SQUARE);
    }

    public static King black() {
        return buildKing(Colour.BLACK, BLACK_KING_SQUARE);
    }

    public static King blackAt(Square at) {
        return buildKing(Colour.BLACK, at);
    }

    /**
     * Use when you want to move the king to a new square, e.g. for a unit test
     * @param forColour
     * @param at
     * @return
     */
    public static King buildKing(Colour forColour, Square at) {
        Square kingLocation = forColour == Colour.WHITE ? WHITE_KING_SQUARE : BLACK_KING_SQUARE;
        King king = new King(forColour, kingLocation);
        king.setCurrentLocation(at);
        return king;
    }

    @Override
    public Set<Move> potentialMoves() {
        return new MovesBuilder(getColour(), getCurrentLocation())
                .forwardOne()
                .backOne()
                .leftOne()
                .rightOne()
                .forwardLeftDiagonal(1)
                .forwardRightDiagonal(1)
                .backLeftDiagonal(1)
                .backRightDiagonal(1)
                // todo castling
                .getMoves();
    }

    @Override
    public int getPieceValue() {
        throw new IllegalStateException("King has no value because it cannot be captured");
    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2654" : "\u265A";
    }
}
