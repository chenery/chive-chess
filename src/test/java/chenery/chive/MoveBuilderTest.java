package chenery.chive;

import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class MoveBuilderTest {

    @Test
    public void initiallyZeroMoves() {
        assertThat(new MoveBuilder(Colour.WHITE, new BoardLocation(Column.A, Row.TWO)).getMoves()).hasSize(0);
    }

    @Test
    public void forwardOne_providesMove() {

        // GIVEN a board location that allows forwardOne
        Set<Move> moves = new MoveBuilder(Colour.WHITE, new BoardLocation(Column.A, Row.TWO)).forwardOne().getMoves();

        // WHEN built, correct move is supplied
        assertThat(moves).hasSize(1).contains(new Move(
                new BoardLocation(Column.A, Row.TWO),
                new BoardLocation(Column.A, Row.THREE)
        ));
    }

    @Test
    public void forwardOne_fromImpossibleBoardLocation_zeroMoves() {

        // GIVEN a board location from which it is not possible to forwardOne
        Set<Move> moves = new MoveBuilder(Colour.WHITE, new BoardLocation(Column.C, Row.EIGHT)).forwardOne().getMoves();

        // WHEN built, no move is supplied
        assertThat(moves).hasSize(0);
    }

    @Test
    public void forwardTwo_providesMove() {

        // GIVEN a board location that allows forwardTwo, and truthy predicate
        Set<Move> moves = new MoveBuilder(Colour.WHITE, new BoardLocation(Column.A, Row.TWO))
                .forwardTwo(location -> true).getMoves();

        // WHEN built, correct move is supplied
        assertThat(moves).hasSize(1).contains(new Move(
                new BoardLocation(Column.A, Row.TWO),
                new BoardLocation(Column.A, Row.FOUR)
        ));
    }

    @Test
    public void forwardTwo_providesZeroMove_ifFalseyPredicate() {

        // GIVEN a board location that allows forwardTwo, and falsey predicate
        Set<Move> moves = new MoveBuilder(Colour.WHITE, new BoardLocation(Column.A, Row.TWO))
                .forwardTwo(location -> false).getMoves();

        // WHEN built, no move is supplied
        assertThat(moves).hasSize(0);
    }

    @Test
    public void forwardOne_forwardTwo_provideBothMoves() {

        // GIVEN a board location that allows forwardOne and forwardTwo, and truthy predicate
        Set<Move> moves = new MoveBuilder(Colour.WHITE, new BoardLocation(Column.A, Row.TWO))
                .forwardOne()
                .forwardTwo(location -> true).getMoves();

        // WHEN built, correct moves are supplied
        assertThat(moves).hasSize(2).contains(
                new Move(
                    new BoardLocation(Column.A, Row.TWO),
                    new BoardLocation(Column.A, Row.FOUR)),
                new Move(
                        new BoardLocation(Column.A, Row.TWO),
                        new BoardLocation(Column.A, Row.THREE))
        );
    }
}
