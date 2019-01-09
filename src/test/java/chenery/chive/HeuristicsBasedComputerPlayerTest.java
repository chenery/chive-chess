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

        // GIVEN a board where white can capture a queen with no loss
        // white player & board for which WHITE can capture black queen
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        King.blackAt(Square.at(Column.H, Row.SEVEN)),
                        Queen.black(),
                        King.white(),
                        Queen.whiteAt(Square.at(Column.D, Row.FIVE)));

        board.print();

        Player player = new HeuristicsBasedComputerPlayer(Colour.WHITE, board);

        // WHEN select move
        Move move = player.selectMove();

        // THEN player to decide to capture the piece over
        assertThat(move.getTo()).isEqualTo(BLACK_QUEEN_SQUARE);
    }

    @Test
    public void selectMove_will_choose_highestValueMove() {

        // GIVEN white player & board for which WHITE can capture black rook and black bishop
        Square blackRookSquare = Square.at(Column.A, Row.EIGHT);
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        King.black(),
                        Rook.blackAt(blackRookSquare),
                        Bishop.blackAt(Square.at(Column.C, Row.EIGHT)),
                        King.white(),
                        Queen.whiteAt(Square.at(Column.B, Row.SEVEN)));

        board.print();

        Player player = new HeuristicsBasedComputerPlayer(Colour.WHITE, board);

        // WHEN select move
        Move move = player.selectMove();

        // THEN move is that to capture Rook, which is more valuable than the bishop
        assertThat(move.getTo()).isEqualTo(blackRookSquare);
    }

    @Test
    public void selectMove_will_consider1MoveAhead_ifNoImmediateScore() {

        // GIVEN a new game board, which after one move WHITE can attack the black rook with bishop
        Board board = new ArrayBasedBoard().setUpAllPieces();
        board.move(Square.at(Column.G, Row.TWO), Square.at(Column.G, Row.THREE));
        board.move(Square.at(Column.B, Row.SEVEN), Square.at(Column.B, Row.SIX));

        board.print();

        Player player = new HeuristicsBasedComputerPlayer(Colour.WHITE, board);

        // WHEN select move
        Move move = player.selectMove();

        // THEN move is that to capture Rook, which is more valuable than the bishop
        assertThat(move).isEqualTo(
                new Move(Square.at(Column.F, Row.ONE), Square.at(Column.G, Row.TWO)));
    }
}
