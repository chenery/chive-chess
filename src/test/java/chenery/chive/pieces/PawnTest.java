package chenery.chive.pieces;

import chenery.chive.Square;
import chenery.chive.Colour;
import chenery.chive.Column;
import chenery.chive.Move;
import chenery.chive.MoveContext;
import chenery.chive.Row;
import org.junit.Test;

import java.util.stream.Stream;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class PawnTest {

    private Square b2 = new Square(Column.B, Row.TWO);
    private Square b3 = new Square(Column.B, Row.THREE);
    private Square b4 = new Square(Column.B, Row.FOUR);
    private Square b7 = new Square(Column.B, Row.SEVEN);

    private Square c1 = new Square(Column.C, Row.ONE);
    private Square c2 = new Square(Column.C, Row.TWO);
    private Square c3 = new Square(Column.C, Row.THREE);
    private Square c4 = new Square(Column.C, Row.FOUR);

    private Square d2 = new Square(Column.D, Row.TWO);
    private Square d3 = new Square(Column.D, Row.THREE);
    private Square d4 = new Square(Column.D, Row.FOUR);

    @Test
    public void potentialMoves_unrestrictedBoardLocation() {

        // GIVEN a white pawn that started at C2
        Pawn pawn = new Pawn(Colour.WHITE, c2);

        // WHEN queried for potential moves when the piece is still at C2
        // THEN all moves are supplied
        assertThat(pawn.potentialMoves()).hasSize(4)
                .contains(
                    new Move(c2, c4),
                    new Move(c2, c3),
                    // potential capture moves
                    new Move(c2, b3),
                    new Move(c2, d3)
                );
    }

    @Test
    public void canMove_fromFirstMove() {

        // GIVEN a white pawn that started at C2
        Pawn pawn = new Pawn(Colour.WHITE, c2);

        // WHEN moves checked can move, THEN correct
        Stream.of(new Move(c2, b2), new Move(c2, b3), new Move(c2, b4), new Move(c2, c1), new Move(c2, c2),
                new Move(c2, d2), new Move(c2, d3), new Move(c2, d4))
            .forEach(invalidMove ->
                    assertThat(pawn.canMove(new MoveContext(Colour.WHITE, invalidMove))).isFalse());

        Stream.of(new Move(c2, c3), new Move(c2, c4))
                .forEach(validMove ->
                        assertThat(pawn.canMove(new MoveContext(Colour.WHITE, validMove))).isTrue());
    }

    @Test
    public void canMove_forwardMoves_withCaptures_areNotAllowed() {

        // GIVEN a white pawn that started at C2
        Pawn whitePawn = new Pawn(Colour.WHITE, c2);
        Pawn blackPawn = new Pawn(Colour.BLACK, b7);

        // WHEN pawn pawn attempts to move forward, but would capture a piece
        MoveContext moveContext = new MoveContext(Colour.WHITE, new Move(c3, c4)).setPieceAtLocation(blackPawn);
        // THEN cannot make move
        assertThat(whitePawn.canMove(moveContext)).isFalse();
    }

    @Test
    public void canMove_diagonal_toCapture() {

        // GIVEN a white pawn that started at C2, and a black pawn
        Pawn whitePawn = new Pawn(Colour.WHITE, c2);
        Pawn blackPawn = new Pawn(Colour.BLACK, b7);

        // WHEN diagonal moves, but no piece to capture
        MoveContext moveContext = new MoveContext(Colour.WHITE, new Move(c3, b4));

        // THEN cannot make move
        assertThat(whitePawn.canMove(moveContext)).isFalse();

        // AND when there is piece to capture
        moveContext.setPieceAtLocation(blackPawn);

        // THEN can make move
        assertThat(whitePawn.canMove(moveContext)).isTrue();
    }
}
