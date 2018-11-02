package chenery.chive;

/**
 * https://en.wikipedia.org/wiki/Portable_Game_Notation
 */
public class BoardLocation {

    private Column column;
    private Row row;

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
}
