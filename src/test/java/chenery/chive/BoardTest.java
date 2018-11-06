package chenery.chive;

import chenery.chive.pieces.Pawn;
import org.junit.Test;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class BoardTest {

    @Test
    public void construct_board() {

        Board board = new Board();
    }

    @Test
    public void construct_board_pawns() {

        // GIVEN A new board
        Board board = new Board();
        assertThat(board).isNotNull();

        for (Row row : Row.values()) {

            for (Column column : Column.values()) {
                BoardLocation pawnLocation = new BoardLocation(row, column);

                if (row == Row.TWO) {
                    // THEN all white pawns exist
                    assertThat(board.getPieceAt(pawnLocation).isPresent()).isTrue();
                    assertThat(board.getPieceAt(pawnLocation).get())
                            .isEqualTo(new Pawn().setColour(Colour.WHITE).setOriginalLocation(pawnLocation));
                } else if (row == Row.SEVEN) {
                    // AND all black pawns exist
                    assertThat(board.getPieceAt(pawnLocation).isPresent()).isTrue();
                    assertThat(board.getPieceAt(pawnLocation).get())
                            .isEqualTo(new Pawn().setColour(Colour.BLACK).setOriginalLocation(pawnLocation));
                } else {
                    // AND no other pieces
                    assertThat(board.getPieceAt(pawnLocation).isPresent()).isFalse();
                }
            }
        }
    }

    @Test
    public void applyMove_forPawn_movesPawn() {

        // GIVEN A new board, with a pawn
        Board board = new Board();
        BoardLocation fromPawnBoardLocation = new BoardLocation(Row.TWO, Column.A);
        BoardLocation toPawnBoardLocation = new BoardLocation(Row.THREE, Column.A);
        Optional<Piece> fromPieceAt = board.getPieceAt(fromPawnBoardLocation);
        assertThat(fromPieceAt.isPresent()).isTrue();
        Piece pawn = fromPieceAt.get();

        // WHEN A pawn is moved
        board.applyMove(fromPawnBoardLocation, toPawnBoardLocation);


        // THEN the board is updated, the 'from' piece is now gone, and the 'to' piece is present
        fromPieceAt = board.getPieceAt(fromPawnBoardLocation);
        assertThat(fromPieceAt.isPresent()).isFalse();

        Optional<Piece> toPieceAt = board.getPieceAt(toPawnBoardLocation);
        assertThat(toPieceAt.isPresent());
        assertThat(pawn).isEqualTo(toPieceAt.get());
    }
}
