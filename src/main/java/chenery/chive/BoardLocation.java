package chenery.chive;

import java.util.Objects;

/**
 * https://en.wikipedia.org/wiki/Portable_Game_Notation
 */
public class BoardLocation {

    private Column column;
    private Row row;

    // todo consider row x column?
    public BoardLocation(Column column, Row row) {
        this.column = column;
        this.row = row;
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
        BoardLocation that = (BoardLocation) o;
        return column == that.column &&
                row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "BoardLocation{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }
}
