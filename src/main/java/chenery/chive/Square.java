package chenery.chive;

import java.util.Optional;

/**
 *
 */
public class Square {

    private Piece piece;

    public Square() {
    }

    public Square withPiece(Piece piece) {
        this.piece = piece;
        return this;
    }

    public Optional<Piece> getPiece() {
        return piece != null ? Optional.of(piece) : Optional.empty();
    }
}
