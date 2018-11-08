package chenery.chive.pieces;


import chenery.chive.BoardLocation;
import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MoveBuilder;
import chenery.chive.MoveContext;
import chenery.chive.Piece;
import chenery.chive.Row;

import java.util.Set;

/**
 *  todo use composition rather than inheritance
 */
public class Pawn extends Piece {

    public Pawn(Colour colour, BoardLocation originalLocation) {
        super(colour, originalLocation);
    }

    @Override
    public boolean canMove(MoveContext moveContext) {

        // 1. Can only move forward
        if (!moveContext.isForwardMove()) {
            return false;
        }

        // 2. Can always move one
        if (moveContext.rowsMoved() == 1 && moveContext.columnsMoved() == 0) {
            return true;
        }

        // 3. Can move two if first move
        if (moveContext.rowsMoved() == 2 && moveContext.columnsMoved() == 0
                && this.isFirstMove(moveContext.getFrom())) {
            return true;
        }

        // todo
        // 3. Can move diagonal - if capturing A piece

        // 4. pawn can turn to any other piece taken? if reaches the end of the board?

        return false;
    }

    @Override
    public Set<Move> potentialMoves(BoardLocation fromBoardLocation) {
        return new MoveBuilder(getColour(), fromBoardLocation)
                .forwardOne()
                .forwardTwo(this::isFirstMove)
                .getMoves();
    }

    /**
     * This first time a pawn is moved it can move 2, so we know it's the first move
     * of the pawn is locations at it's starting position
     *
     * @param boardLocation where the piece is located
     * @return true if first move for this piece
     */
    boolean isFirstMove(BoardLocation boardLocation) {
        return boardLocation.getRow() == Row.TWO && this.getColour() == Colour.WHITE
                || boardLocation.getRow() == Row.SEVEN && this.getColour() == Colour.BLACK;

    }

    @Override
    public String toString() {
        return super.toString() + "P";
    }
}
