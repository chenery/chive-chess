package chenery.chive.pieces;


import chenery.chive.MoveContext;
import chenery.chive.Piece;

/**
 *  todo use composition rather than inheritance
 */
public class Pawn extends Piece {


    @Override
    public boolean canMove(MoveContext moveContext) {

        // todo bring in logic from Game.move for board logic

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
                && moveContext.isFirstMove()) {
            return true;
        }


        // 3. Can move diagonal - if capturing A piece

        // 4. pawn can turn to any other piece taken? if reaches the end of the board?

        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "P";
    }
}
