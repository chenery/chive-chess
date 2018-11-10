package chenery.chive;

/**
 *
 */
public enum Colour {
    WHITE, BLACK;

    public static Colour otherColour(Colour colour) {
        return colour == WHITE ? BLACK : WHITE;
    }
}
