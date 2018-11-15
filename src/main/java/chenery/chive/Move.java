package chenery.chive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static chenery.chive.ColumnDirection.RIGHT;
import static chenery.chive.RowDirection.UP;

/**
 *  Moving from a square, to a square on a chess board.
 */
public class Move {

    private Square from;
    private Square to;

    public Move(Square from, Square to) {
        this.from = from;
        this.to = to;
    }

    /**
     * If the move is more that one square, then some squares will be passed.
     *
     * @return an ordered list of the squares passed exclusive of from and to squares
     */
    public List<Square> getSquaresPassed() {

        List<Square> squares = new ArrayList<>();
        Optional<Direction> optionalDirection = getDirectionForMove();

        // this case covers the knight which doesn't move "straight"
        if (!optionalDirection.isPresent()) {
            return squares;
        }

        Direction moveDirection = optionalDirection.get();
        Optional<Square> optionalNextSquare = from.nextSquare(moveDirection);

        while (optionalNextSquare.isPresent() && !optionalNextSquare.get().equals(to)) {
            Square nextSquare = optionalNextSquare.get();
            squares.add(nextSquare);
            optionalNextSquare = nextSquare.nextSquare(moveDirection);
        }

        return squares;
    }

    /**
     * NOTE: this only works for pieces travelling in a straight line,
     * The knight move will return an empty optional.
     *
     * @return a compass direction for the move.
     */
    public Optional<Direction> getDirectionForMove() {

        final int rows = rowsDistance();
        final int cols = columnDistance();
        final RowDirection rowDirection = rowDirection();
        final ColumnDirection columnDirection = columnDirection();
        Direction direction = null;

        if (rows > 1 && cols == 0) {
            direction = rowDirection == UP ? Direction.N : Direction.S;
        } else if (rows == 0 && cols > 0) {
            direction = columnDirection == RIGHT ? Direction.E : Direction.W;

        } else if (rows == cols) {
            if (rowDirection == UP) {
                direction = columnDirection == RIGHT ? Direction.NE : Direction.NW;
            } else {
                direction = columnDirection == RIGHT ? Direction.SE : Direction.SW;
            }
        }

        // Null direction covers non-linear moves, i.e. the knight
        return direction != null ? Optional.of(direction) : Optional.empty();
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return Objects.equals(from, move.from) &&
                Objects.equals(to, move.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    private ColumnDirection columnDirection() {
        if (from.getColumn().ordinal() == to.getColumn().ordinal()) {
            return ColumnDirection.NONE;
        }

        return from.getColumn().ordinal() < to.getColumn().ordinal() ? RIGHT : ColumnDirection.LEFT;
    }

    private int rowsDistance() {
        return Math.abs(from.getRow().ordinal() - to.getRow().ordinal());
    }

    private RowDirection rowDirection() {
        if (from.getRow().ordinal() == to.getRow().ordinal()) {
            return RowDirection.NONE;
        }

        return from.getRow().ordinal() < to.getRow().ordinal() ? UP : RowDirection.DOWN;
    }

    private int columnDistance() {
        return Math.abs(from.getColumn().ordinal() - to.getColumn().ordinal());
    }
}
