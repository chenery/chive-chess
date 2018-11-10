package chenery.chive;

import org.junit.Test;

import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class MoveValidatorTest {

    @Test
    public void validMoves_forBoardWithOnlyKing() {

        // GIVEN a board with just a king
        Board board = new ArrayBasedBoard().setUpKing(Colour.WHITE);

        // WHEN get valid moves for this board for black player
        Set<Move> validMoves = new MoveValidator().validMoves(Colour.WHITE, board);

        // THEN there should be 6 possible moves
        assertThat(validMoves).hasSize(5);
    }
}
