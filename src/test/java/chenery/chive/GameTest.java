package chenery.chive;

import chenery.chive.MoveResponse.Status;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class GameTest {

    @Test
    public void construct_new_game() {

        Game game = new Game();
        assertThat(game).isNotNull();

        Player white = game.getWhite();
        Player black = game.getBlack();

        assertThat(game.getNextToMove()).isEqualTo(Colour.WHITE);
        assertThat(black).isNotNull();
    }

    @Test
    public void move_initialMove_ok() {

        Game game = new Game();

        MoveResponse moveResponse = game.move(Colour.WHITE, new BoardLocation(Column.d, Row.TWO),
                new BoardLocation(Column.d, Row.THREE));
        assertThat(moveResponse.getStatus()).isEqualTo(Status.OK);
    }

    // todo this test should mock an invalid move. move validation and logic should be encapsulated elsewhere
    @Test
    public void move_initialMove_invalid() {

        Game game = new Game();

        MoveResponse moveResponse = game.move(Colour.WHITE, new BoardLocation(Column.d, Row.TWO),
                new BoardLocation(Column.d, Row.FIVE));
        assertThat(moveResponse.getStatus()).isEqualTo(Status.INVALID);
    }
}
