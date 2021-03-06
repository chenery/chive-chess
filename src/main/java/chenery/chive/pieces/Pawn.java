package chenery.chive.pieces;


import chenery.chive.Square;
import chenery.chive.Colour;
import chenery.chive.Move;
import chenery.chive.MovesBuilder;
import chenery.chive.MoveContext;
import chenery.chive.Piece;
import chenery.chive.Row;

import java.util.Set;

import static chenery.chive.Config.CAPTURE_PAWN_VALUE;

/**
 *  todo use composition rather than inheritance
 */
public class Pawn extends Piece {

    public Pawn(Colour colour, Square originalLocation) {
        super(colour, originalLocation);
    }

    public static Pawn whiteAt(Square originalLocation) {
        return new Pawn(Colour.WHITE, originalLocation);
    }

    public static Pawn blackAt(Square originalLocation) {
        return new Pawn(Colour.BLACK, originalLocation);
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
    public Set<Move> potentialMoves() {
        return new MovesBuilder(getColour(), getCurrentLocation())
                .forwardOne()
                .forwardTwo(this::isFirstMove)
                .forwardLeftDiagonal(1)
                .forwardRightDiagonal(1)
                .getMoves();
    }

    @Override
    public int getPieceValue() {
        return CAPTURE_PAWN_VALUE;
    }

    /**
     * This first time a pawn is moved it can move 2, so we know it's the first move
     * of the pawn is locations at it's starting position
     *
     * @param square where the piece is located
     * @return true if first move for this piece
     */
    private boolean isFirstMove(Square square) {
        return square.getRow() == Row.TWO && this.getColour() == Colour.WHITE
                || square.getRow() == Row.SEVEN && this.getColour() == Colour.BLACK;

    }

    @Override
    public String toString() {
        return getColour() == Colour.WHITE ? "\u2659" : "\u265F";
    }
}
