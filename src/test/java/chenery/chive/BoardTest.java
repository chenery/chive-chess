package chenery.chive;

import chenery.chive.pieces.Bishop;
import chenery.chive.pieces.King;
import chenery.chive.pieces.Knight;
import chenery.chive.pieces.Pawn;
import chenery.chive.pieces.Queen;
import chenery.chive.pieces.Rook;
import org.junit.Test;

import java.util.Optional;

import static chenery.chive.Board.BLACK_KING_SQUARE;
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
                Square square = new Square(column, row);

                if (row == Row.ONE) {
                    assertThat(board.getPiece(square).isPresent()).isTrue();

                    switch (column) {
                        case A:
                            assertThat(board.getPiece(square).get()).isEqualTo(Rook.whiteAt(square));
                            break;
                        case B:
                            assertThat(board.getPiece(square).get()).isEqualTo(Knight.whiteAt(square));
                            break;
                        case C:
                            assertThat(board.getPiece(square).get()).isEqualTo(Bishop.whiteAt(square));
                            break;
                        case D:
                            assertThat(board.getPiece(square).get()).isEqualTo(Queen.whiteAt(square));
                            break;
                        case E:
                            assertThat(board.getPiece(square).get()).isEqualTo(King.whiteAt(square));
                            break;
                        case F:
                            assertThat(board.getPiece(square).get()).isEqualTo(Bishop.whiteAt(square));
                            break;
                        case G:
                            assertThat(board.getPiece(square).get()).isEqualTo(Knight.whiteAt(square));
                            break;
                        case H:
                            assertThat(board.getPiece(square).get()).isEqualTo(Rook.whiteAt(square));
                            break;
                    }

                } else if (row == Row.TWO) {
                    // THEN all white pawns exist
                    assertThat(board.getPiece(square).isPresent()).isTrue();
                    assertThat(board.getPiece(square).get())
                            .isEqualTo(new Pawn(Colour.WHITE, square));
                } else if (row == Row.SEVEN) {
                    // AND all black pawns exist
                    assertThat(board.getPiece(square).isPresent()).isTrue();
                    assertThat(board.getPiece(square).get())
                            .isEqualTo(new Pawn(Colour.BLACK, square));

                } else if (row == Row.EIGHT) {

                    assertThat(board.getPiece(square).isPresent()).isTrue();

                    switch (column) {
                        case A:
                            assertThat(board.getPiece(square).get()).isEqualTo(Rook.blackAt(square));
                            break;
                        case B:
                            assertThat(board.getPiece(square).get()).isEqualTo(Knight.blackAt(square));
                            break;
                        case C:
                            assertThat(board.getPiece(square).get()).isEqualTo(Bishop.blackAt(square));
                            break;
                        case D:
                            assertThat(board.getPiece(square).get()).isEqualTo(Queen.blackAt(square));
                            break;
                        case E:
                            assertThat(board.getPiece(square).get()).isEqualTo(King.blackAt(square));
                            break;
                        case F:
                            assertThat(board.getPiece(square).get()).isEqualTo(Bishop.blackAt(square));
                            break;
                        case G:
                            assertThat(board.getPiece(square).get()).isEqualTo(Knight.blackAt(square));
                            break;
                        case H:
                            assertThat(board.getPiece(square).get()).isEqualTo(Rook.blackAt(square));
                            break;
                    }

                } else {
                    // AND no other pieces
                    assertThat(board.getPiece(square).isPresent()).isFalse();
                }
            }
        }
    }

    @Test
    public void getPiece_forValidPiece() {

        // GIVEN a full board
        Board board = new ArrayBasedBoard().setUpAllPieces();

        // WHEN we get the black king
        Optional<Piece> optionalPiece = board.getPiece(BLACK_KING_SQUARE);

        // THEN piece is correct
        assertThat(optionalPiece.isPresent()).isTrue();
        Piece king = optionalPiece.get();
        assertThat(king).isEqualTo(new King(Colour.BLACK, BLACK_KING_SQUARE));
    }

    @Test
    public void getPiece_forInValidPiece() {

        // GIVEN a full board
        Board board = new ArrayBasedBoard().setUpAllPieces();

        // WHEN we get a non-existent piece
        Optional<Piece> optionalPiece = board.getPiece(new Square(Column.A, Row.FOUR));

        // THEN a piece is not provided
        assertThat(optionalPiece.isPresent()).isFalse();
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
        Square fromPawnSquare = new Square(Column.A, Row.TWO);
        Square toPawnSquare = new Square(Column.A, Row.THREE);
        Optional<Piece> fromPieceAt = board.getPiece(fromPawnSquare);
        assertThat(fromPieceAt.isPresent()).isTrue();
        Piece pawn = fromPieceAt.get();

        // WHEN A pawn is moved
        board.move(fromPawnSquare, toPawnSquare);


        // THEN the board is updated, the 'from' piece is now gone, and the 'to' piece is present
        fromPieceAt = board.getPiece(fromPawnSquare);
        assertThat(fromPieceAt.isPresent()).isFalse();

        Optional<Piece> toPieceAt = board.getPiece(toPawnSquare);
        assertThat(toPieceAt.isPresent());
        assertThat(pawn).isEqualTo(toPieceAt.get());
    }
}
