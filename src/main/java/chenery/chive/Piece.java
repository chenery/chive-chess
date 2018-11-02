package chenery.chive;

import java.util.Objects;

/**
 * todo a piece should move?
 */
public abstract class Piece {

    private Colour colour;

    public abstract boolean canMove(BoardLocation from, BoardLocation to);

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
