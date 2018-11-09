package chenery.chive.pieces;


import chenery.chive.BoardLocation;
import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
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

        final Move proposedMove = moveContext.getMove();

        // 1 & 2 forward moves

        // todo check 2 forward does not "jump" a piece

        if (new MovesBuilder(getColour(), moveContext.getFrom())
                .forwardOne()
                .forwardTwo(this::isFirstMove)
                .getMoves()
                .contains(proposedMove)
                // forward moves are only possible if the square is unoccupied
                && !moveContext.getPieceAtToLocation().isPresent()) {
            return true;
        }

        // 3. Can move diagonal - if capturing a piece
        // NOTE: assume Game class has checked any potential capture is owned by opposition
        if (moveContext.getPieceAtToLocation().isPresent()
                && new MovesBuilder(getColour(), moveContext.getFrom())
                .forwardLeftDiagonal(1)
                .forwardRightDiagonal(1)
                .getMoves()
                .contains(proposedMove)) {
            return true;
        }

        // todo
        // 4. pawn can turn to any other piece taken? if reaches the end of the board?

        return false;
    }

    @Override
    public Set<Move> potentialMoves(BoardLocation fromBoardLocation) {
        return new MovesBuilder(getColour(), fromBoardLocation)
                .forwardOne()
                .forwardTwo(this::isFirstMove)
                .forwardLeftDiagonal(1)
                .forwardRightDiagonal(1)
                .getMoves();
    }

    /**
     * This first time a pawn is moved it can move 2, so we know it's the first move
     * of the pawn is locations at it's starting position
     *
     * @param boardLocation where the piece is located
     * @return true if first move for this piece
     */
    private boolean isFirstMove(BoardLocation boardLocation) {
        return boardLocation.getRow() == Row.TWO && this.getColour() == Colour.WHITE
                || boardLocation.getRow() == Row.SEVEN && this.getColour() == Colour.BLACK;

    }

    @Override
    public String toString() {
        return super.toString() + "P";
    }
}
