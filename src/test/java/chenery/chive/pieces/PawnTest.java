package chenery.chive.pieces;

import chenery.chive.BoardLocation;
import chenery.chive.Colour;
import chenery.chive.Column;
import chenery.chive.Move;
import chenery.chive.Row;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class PawnTest {

    @Test
    public void potentialMoves_unrestrictedBoardLocation() {

        // GIVEN a white pawn that started at C2
        BoardLocation c2 = new BoardLocation(Column.C, Row.TWO);
        Pawn pawn = new Pawn(Colour.WHITE, c2);

        // WHEN queried for potential moves when the piece is still at C2
        // THEN all moves are supplied
        assertThat(pawn.potentialMoves(c2)).hasSize(2)
                .contains(
                    new Move(
                            new BoardLocation(Column.C, Row.TWO),
                            new BoardLocation(Column.C, Row.FOUR)),
                    new Move(
                            new BoardLocation(Column.C, Row.TWO),
                            new BoardLocation(Column.C, Row.THREE))
                );

        // todo add further pawn moves
    }
}
