package chenery.chive;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *  A builder to supply a set of moves given a simple description
 */
public class MovesBuilder {

    private Colour colour;
    private Square from;
    private Set<Move> moves = new HashSet<>();

    public MovesBuilder(Colour colour, Square from) {
        this.colour = colour;
        this.from = from;
    }

    public Set<Move> getMoves() {
        // The returned collection is unmodifiable for the caller and the moves are immutable.
        return Collections.unmodifiableSet(moves);
    }

    /**
     * Any number of rows forward
     * @return a reference to self
     */
    public MovesBuilder forward() {
        allSquares(this::forward);
        return this;
    }

    public MovesBuilder backward() {
        allSquares(this::backward);
        return this;
    }

    public MovesBuilder left() {
        allSquares(this::left);
        return this;
    }

    public MovesBuilder right() {
        allSquares(this::right);
        return this;
    }

    public MovesBuilder forwardOne() {
        forward(1);
        return this;
    }

    public MovesBuilder forwardTwo(Predicate<Square> condition) {
        if (condition.test(from)) {
            forward(2);
        }
        return this;
    }

    public MovesBuilder backOne() {
        move(false, 1, true, 0);
        return this;
    }

    public MovesBuilder leftOne() {
        move(true, 0, false, 1);
        return this;
    }

    public MovesBuilder rightOne() {
        move(true, 0, true, 1);
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

    public MovesBuilder backLeftDiagonal(int distance) {
        move(false, distance, false, distance);
        return this;
    }

    public MovesBuilder backRightDiagonal(int distance) {
        move(false, distance, true, distance);
        return this;
    }

    private MovesBuilder forward(int numberRows) {
        move(true, numberRows, true, 0);
        return this;
    }

    private MovesBuilder backward(int numberRows) {
        move(false, numberRows, true, 0);
        return this;
    }

    private MovesBuilder left(int numberRows) {
        move(true, 0, false, numberRows);
        return this;
    }

    private MovesBuilder right(int numberRows) {
        move(true, 0, true, numberRows);
        return this;
    }

    private void move(boolean isForward, int rowDistance, boolean isRight, int columnDistance) {
        int fromColumnOrdinal = from.getColumn().ordinal();
        int toColumnOrdinal = isRight ? fromColumnOrdinal + columnDistance : fromColumnOrdinal - columnDistance;

        int fromRowOrdinal = from.getRow().ordinal();
        int toRowOrdinal = toRowOrdinal(isForward, rowDistance, fromRowOrdinal);

        Optional<Column> toColumnOptional = Column.getByOrdinal(toColumnOrdinal);
        Optional<Row> toRowOptional = Row.getByOrdinal(toRowOrdinal);

        if (toColumnOptional.isPresent() && toRowOptional.isPresent()) {
            moves.add(new Move(from, new Square(toColumnOptional.get(), toRowOptional.get())));
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

    private static void allSquares(Function<Integer, MovesBuilder> function) {
        for (int i = 1 ; i <= 8; i++) {
            function.apply(i);
        }
    }
}
