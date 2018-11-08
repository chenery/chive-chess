package chenery.chive;

import java.util.Optional;

/**
 *
 */
public enum Row {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;

    public static Optional<Row> getByOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal > 7) {
            return Optional.empty();
        }
        return Optional.of(Row.values()[ordinal]);
    }

    public static Optional<Row> get(int rowNumber) {
        return getByOrdinal(rowNumber - 1);
    }
}
