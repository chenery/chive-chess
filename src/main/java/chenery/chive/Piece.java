package chenery.chive;

import java.util.Objects;

/**
 * todo a piece should move?
 *
 * todo maybe you can get all available moves for a piece given a board
 */
public abstract class Piece {

    private Colour colour;

    public abstract boolean canMove(Move move);

    public Piece setColour(Colour colour) {
        this.colour = colour;
        return this;
    }

    public Colour getColour() {
        return this.colour;
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
        return colour == piece.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "colour=" + colour +
                '}';
    }
}
