package chenery.chive.pieces;


import chenery.chive.BoardLocation;
import chenery.chive.Piece;

/**
 *
 */
public class Pawn extends Piece {


    @Override
    public boolean canMove(BoardLocation from, BoardLocation to) {
        return false;
    }
}
