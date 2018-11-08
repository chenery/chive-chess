package chenery.chive;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 *
 */
public class MoveBuilder {

    private Colour colour;
    private BoardLocation from;
    private Set<Move> moves = new HashSet<>();

    public MoveBuilder(Colour colour, BoardLocation from) {
        this.colour = colour;
        this.from = from;
    }

    public Set<Move> getMoves() {
        // The returned collection is unmodifiable for the caller and the moves are immutable.
        return Collections.unmodifiableSet(moves);
    }

    public MoveBuilder forwardOne() {
        forward(1);
        return this;
    }

    public MoveBuilder forwardTwo(Predicate<BoardLocation> condition) {
        if (condition.test(from)) {
            forward(2);
        }
        return this;
    }

    private void forward(int numberRows) {
        int fromRowOrdinal = from.getRow().ordinal();
        int toRowOrdinal = colour == Colour.WHITE ? fromRowOrdinal + numberRows : fromRowOrdinal - numberRows;
        Optional<Row> toRowOptional = Row.getByOrdinal(toRowOrdinal);
        toRowOptional.ifPresent(row -> moves.add(new Move(from, new BoardLocation(from.getColumn(), row))));
    }
}
