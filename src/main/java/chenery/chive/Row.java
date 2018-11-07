package chenery.chive;

/**
 *
 */
public enum Row {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;

    public static Row getByOrdinal(int ordinal) {
        // Note we might throw our own exception here if the ordinal was out of range of the enum
        return Row.values()[ordinal];
    }

    public static Row get(int rowNumber) {
        // Note we might throw our own exception here if the ordinal was out of range of the enum
        return getByOrdinal(rowNumber - 1);
    }
}
