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
        return forward(1);
    }

    public MovesBuilder forwardTwo(Predicate<Square> condition) {
        if (condition.test(from)) {
            forward(2);
        }
        return this;
    }

    public MovesBuilder backOne() {
        return move(false, 1, true, 0);
    }

    public MovesBuilder leftOne() {
        return move(true, 0, false, 1);
    }

    public MovesBuilder rightOne() {
        return move(true, 0, true, 1);
    }

    public MovesBuilder forwardLeftDiagonal(int distance) {
        return move(true, distance, false, distance);
    }

    public MovesBuilder forwardRightDiagonal(int distance) {
        return move(true, distance, true, distance);
    }

    public MovesBuilder backLeftDiagonal(int distance) {
        return move(false, distance, false, distance);
    }

    public MovesBuilder backRightDiagonal(int distance) {
        return move(false, distance, true, distance);
    }

    public MovesBuilder move(boolean isForward, int rowDistance, boolean isRight, int columnDistance) {

        RowDirection rowDirection;
        ColumnDirection columnDirection;
        if (colour == Colour.WHITE) {
            rowDirection = isForward ? RowDirection.UP : RowDirection.DOWN;
            columnDirection = isRight ? ColumnDirection.RIGHT : ColumnDirection.LEFT;
        } else {
            rowDirection = isForward ? RowDirection.DOWN : RowDirection.UP;
            columnDirection = isRight ? ColumnDirection.LEFT : ColumnDirection.RIGHT;
        }

        Optional<Square> toSquare = from.toSquare(rowDirection, rowDistance, columnDirection, columnDistance);
        toSquare.ifPresent(square -> moves.add(new Move(from, square)));
        return this;
    }

    private MovesBuilder forward(int numberRows) {
        return move(true, numberRows, true, 0);
    }

    private MovesBuilder backward(int numberRows) {
        return move(false, numberRows, true, 0);
    }

    private MovesBuilder left(int numberRows) {
        return move(true, 0, false, numberRows);
    }

    private MovesBuilder right(int numberRows) {
        return move(true, 0, true, numberRows);
    }

    private static void allSquares(Function<Integer, MovesBuilder> function) {
        for (int i = 1 ; i <= 8; i++) {
            function.apply(i);
        }
    }
}
