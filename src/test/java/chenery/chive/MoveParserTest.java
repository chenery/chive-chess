package chenery.chive;

import org.junit.Test;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class MoveParserTest {

    @Test
    public void parse_ok() {

        // GIVEN an ok move
        String moveText = "a2,a3";

        // WHEN parse is called
        Optional<Move> optionalMove = MoveParser.parse(moveText);

        // THEN move returned
        assertThat(optionalMove.isPresent()).isTrue();
        assertThat(optionalMove.get()).isEqualTo(
                new Move(new BoardLocation(Column.A, Row.TWO),
                        new BoardLocation(Column.A, Row.THREE)));
    }

    @Test
    public void parse_ok_withinBoardRange() {

        // GIVEN an ok move
        String moveText = "a1,h8";

        // WHEN parse is called
        Optional<Move> optionalMove = MoveParser.parse(moveText);

        // THEN move returned
        assertThat(optionalMove.isPresent()).isTrue();
        assertThat(optionalMove.get()).isEqualTo(
                new Move(new BoardLocation(Column.A, Row.ONE),
                        new BoardLocation(Column.H, Row.EIGHT)));
    }

    @Test
    public void parse_moveOutOfRange() {

        // GIVEN a move that extends beyond the board
        String moveText = "a1,i8";

        // WHEN parse is called
        Optional<Move> optionalMove = MoveParser.parse(moveText);

        // THEN move is not returned
        assertThat(optionalMove.isPresent()).isFalse();
    }

    @Test
    public void parse_twoMoves_takesFirst() {

        // GIVEN an ok move followed by a second move
        String moveText = "a2,a3 b7,c2";

        // WHEN parse is called
        Optional<Move> optionalMove = MoveParser.parse(moveText);

        // THEN move returned
        assertThat(optionalMove.isPresent()).isTrue();
        assertThat(optionalMove.get()).isEqualTo(
                new Move(new BoardLocation(Column.A, Row.TWO),
                        new BoardLocation(Column.A, Row.THREE)));
    }

    @Test
    public void parse_twoMoves_noSpace_takesFirst() {

        // GIVEN an ok move followed by a second move
        String moveText = "a2,a3b7,c2";

        // WHEN parse is called
        Optional<Move> optionalMove = MoveParser.parse(moveText);

        // THEN move returned
        assertThat(optionalMove.isPresent()).isTrue();
        assertThat(optionalMove.get()).isEqualTo(
                new Move(new BoardLocation(Column.A, Row.TWO),
                        new BoardLocation(Column.A, Row.THREE)));
    }

    @Test
    public void parse_noText() {

        // GIVEN no move text
        String moveText = "";

        // WHEN parse is called
        Optional<Move> optionalMove = MoveParser.parse(moveText);

        // THEN move is not returned
        assertThat(optionalMove.isPresent()).isFalse();
    }

    @Test
    public void parse_nullText() {

        // WHEN parse is called with null move text
        Optional<Move> optionalMove = MoveParser.parse(null);

        // THEN move is not returned
        assertThat(optionalMove.isPresent()).isFalse();
    }
}
