package chenery.chive;

import chenery.chive.pieces.King;
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

        // GIVEN A new board
        Board board = new ArrayBasedBoard().setUpAllPieces();
        assertThat(board).isNotNull();

        for (Row row : Row.values()) {

            for (Column column : Column.values()) {
                BoardLocation testLocation = new BoardLocation(column, row);

                if (row == Row.ONE) {
                    if (column == Column.E) {
                        assertThat(board.getPiece(testLocation).isPresent()).isTrue();
                        assertThat(board.getPiece(testLocation).get())
                                .isEqualTo(new King(Colour.WHITE, testLocation));
                    }

                } else if (row == Row.TWO) {
                    // THEN all white pawns exist
                    assertThat(board.getPiece(testLocation).isPresent()).isTrue();
                    assertThat(board.getPiece(testLocation).get())
                            .isEqualTo(new Pawn(Colour.WHITE, testLocation));
                } else if (row == Row.SEVEN) {
                    // AND all black pawns exist
                    assertThat(board.getPiece(testLocation).isPresent()).isTrue();
                    assertThat(board.getPiece(testLocation).get())
                            .isEqualTo(new Pawn(Colour.BLACK, testLocation));

                } else if (row == Row.EIGHT) {

                    if (column == Column.E) {
                        assertThat(board.getPiece(testLocation).isPresent()).isTrue();
                        assertThat(board.getPiece(testLocation).get())
                                .isEqualTo(new King(Colour.BLACK, testLocation));
                    }

                } else {
                    // AND no other pieces
                    assertThat(board.getPiece(testLocation).isPresent()).isFalse();
                }
            }
        }
    }

    @Test
    public void getPieces_forPawns() {

        // GIVEN a board with just pawns
        Board board = new ArrayBasedBoard().setUpPawns(Colour.WHITE).setUpPawns(Colour.BLACK);

        // WHEN getPieces then we get the 8 pawns
        assertThat(board.getPieces(Colour.BLACK)).hasSize(8);
    }

    @Test
    public void applyMove_forPawn_movesPawn() {

        // GIVEN A new board, with a pawn
        Board board = new ArrayBasedBoard().setUpAllPieces();
        BoardLocation fromPawnBoardLocation = new BoardLocation(Column.A, Row.TWO);
        BoardLocation toPawnBoardLocation = new BoardLocation(Column.A, Row.THREE);
        Optional<Piece> fromPieceAt = board.getPiece(fromPawnBoardLocation);
        assertThat(fromPieceAt.isPresent()).isTrue();
        Piece pawn = fromPieceAt.get();

        // WHEN A pawn is moved
        board.move(fromPawnBoardLocation, toPawnBoardLocation);


        // THEN the board is updated, the 'from' piece is now gone, and the 'to' piece is present
        fromPieceAt = board.getPiece(fromPawnBoardLocation);
        assertThat(fromPieceAt.isPresent()).isFalse();

        Optional<Piece> toPieceAt = board.getPiece(toPawnBoardLocation);
        assertThat(toPieceAt.isPresent());
        assertThat(pawn).isEqualTo(toPieceAt.get());
    }
}
