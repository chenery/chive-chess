package chenery.chive;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Converts chess notation to a Move (https://en.wikipedia.org/wiki/Algebraic_notation_(chess))
 *
 *  We will use our own notation, as the algebraic chess notification is optimised and required an understanding of the
 *  game - what pieces can move where etc.
 *
 *  Format: ${fromColumn}${fromRow},${toColumn}${toRow}
 *
 *  e.g. a2,a3      means move the piece at a2 to a3.
 */
public class MoveParser {

    private final static String MOVE_REGEX = "([a-h]){1}([1-8]){1},([a-h]){1}([1-8]){1}";
    private final static Pattern MOVE_PATTERN = Pattern.compile(MOVE_REGEX);

    public static Optional<Move> parse(String moveText) {

        if (moveText == null) {
            return Optional.empty();
        }

        Matcher matcher = MOVE_PATTERN.matcher(moveText);

        if (matcher.find()) {
            Column fromColumn = Column.getColumn(matcher.group(1));
            Row fromRow = Row.get(Integer.parseInt(matcher.group(2)));
            Column toColumn = Column.getColumn(matcher.group(3));
            Row toRow = Row.get(Integer.parseInt(matcher.group(4)));

            return Optional.of(new Move(
                            new BoardLocation(fromColumn, fromRow),
                            new BoardLocation(toColumn, toRow)));
        } else {
            return Optional.empty();
        }
    }
}
