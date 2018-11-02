package chenery.chive;

import chenery.chive.pieces.Pawn;
import org.junit.Test;

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

        // GIVEN a new board
        Board board = new Board();
        assertThat(board).isNotNull();

        for (Row row : Row.values()) {

            for (Column column : Column.values()) {
                BoardLocation pawnLocation = new BoardLocation(column, row);

                if (row == Row.TWO) {
                    // THEN all white pawns exist
                    assertThat(board.getPieceAt(pawnLocation).isPresent()).isTrue();
                    assertThat(board.getPieceAt(pawnLocation).get())
                            .isEqualTo(new Pawn().setColour(Colour.WHITE));
                } else if (row == Row.SEVEN) {
                    // AND all black pawns exist
                    assertThat(board.getPieceAt(pawnLocation).isPresent()).isTrue();
                    assertThat(board.getPieceAt(pawnLocation).get())
                            .isEqualTo(new Pawn().setColour(Colour.BLACK));
                } else {
                    // AND no other pieces
                    assertThat(board.getPieceAt(pawnLocation).isPresent()).isFalse();
                }
            }
        }
    }
}
