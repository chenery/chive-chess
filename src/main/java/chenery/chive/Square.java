package chenery.chive;

import java.util.Optional;

/**
 *
 */
public class Square {

    private Piece piece;
    private BoardLocation atBoardLocation;

    public Square setPiece(Piece piece) {
        this.piece = piece;
        return this;
    }

    public Square setAtBoardLocation(BoardLocation atBoardLocation) {
        this.atBoardLocation = atBoardLocation;
        return this;
    }

    public Optional<Piece> getPiece() {
        return piece != null ? Optional.of(piece) : Optional.empty();
    }

    public BoardLocation getAtBoardLocation() {
        return atBoardLocation;
    }

    /**
     * Remove the Piece from the Square by setting to null
     * @return the Piece that was removed, or empty is there was no piece there.
     */
    public Optional<Piece> removePiece() {
        Optional<Piece> optionalPiece = getPiece();
        this.piece = null;
        return optionalPiece;
    }
}
