package chenery.chive;

import java.util.Objects;
import java.util.Set;

/**
 * todo A piece should move?
 *
 * todo a piece could know it's own board location?
 *
 */
public abstract class Piece {

    private Colour colour;

    // A way of uniquely identifying the piece when the are multiple instances, e.g. Pawn
    private BoardLocation originalLocation;

    public Piece(Colour colour, BoardLocation originalLocation) {
        this.colour = colour;
        this.originalLocation = originalLocation;
    }

    /**
     * All moves a piece can make from a board location.  This does not take into account
     * any other pieces on the board
     *
     * @param fromBoardLocation
     * @return
     */
    public abstract Set<Move> potentialMoves(BoardLocation fromBoardLocation);

    public abstract boolean canMove(MoveContext moveContext);

    public Colour getColour() {
        return this.colour;
    }

    public BoardLocation getOriginalLocation() {
        return originalLocation;
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
