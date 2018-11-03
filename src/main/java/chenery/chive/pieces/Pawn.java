package chenery.chive.pieces;


import chenery.chive.Move;
import chenery.chive.Piece;

/**
 *  todo use composition rather than inheritance
 */
public class Pawn extends Piece {


    @Override
    public boolean canMove(Move move) {

        // todo bring in logic from Game.move for board logic

        // 1. Can only move forward
        if (!move.isForwardMove()) {
            return false;
        }

        // 2. Can always move one
        if (move.rowsMoved() == 1 && move.columnsMoved() == 0) {
            return true;
        }

        // 3. Can move two if first move
        if (move.rowsMoved() == 2 && move.columnsMoved() == 0
                && move.isFirstMove()) {
            return true;
        }


        // 3. Can move diagonal - if capturing a piece

        // 4. pawn can turn to any other piece taken? if reaches the end of the board?

        return false;
    }

}
