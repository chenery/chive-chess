package chenery.chive;

import java.util.Objects;
import java.util.Optional;

/**
 * https://en.wikipedia.org/wiki/Portable_Game_Notation
 */
public class Square {

    private Column column;
    private Row row;

    public Square(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    /**
     * Basic static builder method for square
     * @param column at this column
     * @param row at this row
     * @return a new square
     */
    public static Square at(Column column, Row row) {
        return new Square(column, row)  ;
    }

    public Optional<Square> nextSquare(Direction direction) {

        switch (direction) {
            case N:
                return toSquare(RowDirection.UP, 1, ColumnDirection.RIGHT, 0);
            case NE:
                return toSquare(RowDirection.UP, 1, ColumnDirection.RIGHT, 1);
            case E:
                return toSquare(RowDirection.UP, 0, ColumnDirection.RIGHT, 1);
            case SE:
                return toSquare(RowDirection.DOWN, 1, ColumnDirection.RIGHT, 1);
            case S:
                return toSquare(RowDirection.DOWN, 1, ColumnDirection.RIGHT, 0);
            case SW:
                return toSquare(RowDirection.DOWN, 1, ColumnDirection.LEFT, 1);
            case W:
                return toSquare(RowDirection.DOWN, 0, ColumnDirection.LEFT, 1);
            case NW:
                return toSquare(RowDirection.UP, 1, ColumnDirection.LEFT, 1);
            default:
                throw new IllegalArgumentException("Missing square for direction " + direction.name());
        }
    }

    /**
     * Builder method to get a square some relative distance from this square.
     *
     * todo this signature suits a builder as not all arguments are always needed
     *
     * @param rowDirection
     * @param rowDistance
     * @param colDirection
     * @param colDistance
     * @return Optionally a new square, or empty if beyond the constraints of the board.
     */
    public Optional<Square> toSquare(RowDirection rowDirection, int rowDistance,
                                      ColumnDirection colDirection, int colDistance) {
        int fromColumnOrdinal = this.getColumn().ordinal();
        int toColumnOrdinal = colDirection == ColumnDirection.RIGHT
                ? fromColumnOrdinal + colDistance : fromColumnOrdinal - colDistance;

        int fromRowOrdinal = this.getRow().ordinal();
        int toRowOrdinal = rowDirection == RowDirection.UP
                ? fromRowOrdinal + rowDistance : fromRowOrdinal - rowDistance;

        Optional<Column> toColumnOptional = Column.getByOrdinal(toColumnOrdinal);
        Optional<Row> toRowOptional = Row.getByOrdinal(toRowOrdinal);

        if (toColumnOptional.isPresent() && toRowOptional.isPresent()) {
            return Optional.of(Square.at(toColumnOptional.get(), toRowOptional.get()));
        }

        return Optional.empty();
    }

    public Column getColumn() {
        return column;
    }

    public Row getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Square that = (Square) o;
        return column == that.column &&
                row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "Square{" + column + "," + row +'}';
    }
}
