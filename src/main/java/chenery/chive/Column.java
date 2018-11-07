package chenery.chive;

/**
 *
 */
public enum Column {
    A, B, C, D, E, F, G, H;

    public static Column getColumn(String columnName) {
        for (Column column : Column.values()) {
            if (column.name().equalsIgnoreCase(columnName)) {
                return column;
            }
        }
        throw new IllegalArgumentException("Column name provided does not match a column");
    }

    public static Column getByOrdinal(int ordinal) {
        // Note we might throw our own exception here if the ordinal was out of range of the enum
        return Column.values()[ordinal];
    }
}
