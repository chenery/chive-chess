package chenery.chive.pieces;

import chenery.chive.BoardLocation;
import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MoveContext;
import chenery.chive.MovesBuilder;
import chenery.chive.Piece;

import java.util.Set;

/**
 *
 */
public class King extends Piece {

    public King(Colour colour, BoardLocation originalLocation) {
        super(colour, originalLocation);
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
    public boolean canMove(MoveContext moveContext) {
        return potentialMoves().contains(moveContext.getMove());
    }

    @Override
    public String toString() {
        return super.toString() + "K";
    }
}
