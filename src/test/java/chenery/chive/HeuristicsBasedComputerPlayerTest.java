package chenery.chive;

import chenery.chive.pieces.Bishop;
import chenery.chive.pieces.King;
import chenery.chive.pieces.Queen;
import chenery.chive.pieces.Rook;
import org.junit.Test;

import static chenery.chive.Board.BLACK_QUEEN_SQUARE;
import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class HeuristicsBasedComputerPlayerTest {

    @Test
    public void selectMove_will_capturePiece() {

        // GIVEN white player & board for which WHITE can capture black queen
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        King.black(),
                        Queen.black(),
                        King.white(),
                        Queen.whiteAt(Square.at(Column.D, Row.FIVE)));

        Player player = new HeuristicsBasedComputerPlayer(Colour.WHITE, board);

        // WHEN select move
        Move move = player.selectMove();

        // THEN move is that to capture Queen
        assertThat(move.getTo()).isEqualTo(BLACK_QUEEN_SQUARE);
    }

    @Test
    public void selectMove_will_capture_highestValuePiece() {

        // GIVEN white player & board for which WHITE can capture black rook and black bishop
        Square blackRookSquare = Square.at(Column.A, Row.EIGHT);
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        King.black(),
                        Rook.blackAt(blackRookSquare),
                        Bishop.blackAt(Square.at(Column.C, Row.EIGHT)),
                        King.white(),
                        Queen.whiteAt(Square.at(Column.B, Row.SEVEN)));

        Player player = new HeuristicsBasedComputerPlayer(Colour.WHITE, board);

        // WHEN select move
        Move move = player.selectMove();

        // THEN move is that to capture Rook, which is more valuable than the bishop
        assertThat(move.getTo()).isEqualTo(blackRookSquare);
    }
}
