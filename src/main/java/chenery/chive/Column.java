package chenery.chive;

import java.util.Optional;

/**
 *
 */
public enum Column {
    A, B, C, D, E, F, G, H;

    public static Optional<Column> getColumn(String columnName) {
        for (Column column : Column.values()) {
            if (column.name().equalsIgnoreCase(columnName)) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }

    public static Optional<Column> getByOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal > 7) {
            return Optional.empty();
        }
        return Optional.of(Column.values()[ordinal]);
    }
}
