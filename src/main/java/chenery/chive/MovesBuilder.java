package chenery.chive;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 *  A builder to supply a set of moves given a simple description
 */
public class MovesBuilder {

    private Colour colour;
    private BoardLocation from;
    private Set<Move> moves = new HashSet<>();

    public MovesBuilder(Colour colour, BoardLocation from) {
        this.colour = colour;
        this.from = from;
    }

    public Set<Move> getMoves() {
        // The returned collection is unmodifiable for the caller and the moves are immutable.
        return Collections.unmodifiableSet(moves);
    }

    public MovesBuilder forwardOne() {
        forward(1);
        return this;
    }

    public MovesBuilder forwardTwo(Predicate<BoardLocation> condition) {
        if (condition.test(from)) {
            forward(2);
        }
        return this;
    }

    public MovesBuilder forwardLeftDiagonal(int distance) {
        move(true, distance, false, distance);
        return this;
    }

    public MovesBuilder forwardRightDiagonal(int distance) {
        move(true, distance, true, distance);
        return this;
    }

    private void forward(int numberRows) {
        move(true, numberRows, true, 0);
    }

    private void move(boolean isForward, int rowDistance, boolean isRight, int columnDistance) {
        int fromColumnOrdinal = from.getColumn().ordinal();
        int toColumnOrdinal = isRight ? fromColumnOrdinal + columnDistance : fromColumnOrdinal - columnDistance;

        int fromRowOrdinal = from.getRow().ordinal();
        int toRowOrdinal = toRowOrdinal(isForward, rowDistance, fromRowOrdinal);

        Optional<Column> toColumnOptional = Column.getByOrdinal(toColumnOrdinal);
        Optional<Row> toRowOptional = Row.getByOrdinal(toRowOrdinal);

        if (toColumnOptional.isPresent() && toRowOptional.isPresent()) {
            moves.add(new Move(from, new BoardLocation(toColumnOptional.get(), toRowOptional.get())));
        }
    }

    private int toRowOrdinal(boolean isForward, int rowDistance, int fromRowOrdinal) {

        if (colour == Colour.WHITE) {
            // moving up the board
            return isForward ? fromRowOrdinal + rowDistance : fromRowOrdinal - rowDistance;
        } else {
            // moving down the board
            return isForward ? fromRowOrdinal - rowDistance : fromRowOrdinal + rowDistance;
        }
    }
}
