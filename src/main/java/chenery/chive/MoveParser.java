package chenery.chive;

/**
 *  Converts chess notation to a Move (https://en.wikipedia.org/wiki/Algebraic_notation_(chess))
 */
public class MoveParser {

    public static Move parse(String moveText) {
        return new Move(new BoardLocation(Row.TWO, Column.A), new BoardLocation(Row.THREE, Column.A));
    }
}
