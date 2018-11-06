package chenery.chive;

import java.util.Objects;

/**
 * todo A piece should move?
 *
 * todo maybe you can get all available moves for A piece given A board
 */
public abstract class Piece {

    private Colour colour;
    // A way of uniquely identifying the piece when the are multiple instances, e.g. Pawn
    private BoardLocation originalLocation;

    public abstract boolean canMove(MoveContext moveContext);

    public Piece setColour(Colour colour) {
        this.colour = colour;
        return this;
    }

    public Piece setOriginalLocation(BoardLocation originalLocation) {
        this.originalLocation = originalLocation;
        return this;
    }

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
