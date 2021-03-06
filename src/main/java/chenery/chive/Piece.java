package chenery.chive;

import java.util.Objects;
import java.util.Set;

/**
 * todo create piece interface
 *
 *
 */
public abstract class Piece {

    private Colour colour;

    // A way of uniquely identifying the piece when the are multiple instances, e.g. Pawn
    private Square originalLocation;

    private Square currentLocation;

    public Piece(Colour colour, Square originalLocation) {
        this.colour = colour;
        this.originalLocation = originalLocation;
        this.currentLocation = originalLocation;
    }

    public Piece setCurrentLocation(Square currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    /**
     * All moves a piece can make from a board location.  This does not take into account
     * any other pieces on the board
     *
     * @return
     */
    public abstract Set<Move> potentialMoves();

    public abstract int getPieceValue();

    public boolean canMove(MoveContext moveContext) {
        return potentialMoves().contains(moveContext.getMove());
    }

    public Colour getColour() {
        return this.colour;
    }

    public Square getOriginalLocation() {
        return originalLocation;
    }

    public Square getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return colour == piece.colour && originalLocation.equals(piece.getOriginalLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, originalLocation);
    }

    @Override
    public String toString() {
        return (colour == Colour.WHITE ? "W" : "B");
    }
}
