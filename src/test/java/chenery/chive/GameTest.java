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

        Game game = Game.singlePlayerGame();
        assertThat(game).isNotNull();

        Player white = game.getWhite();
        Player black = game.getBlack();

        assertThat(game.getNextToMove()).isEqualTo(Colour.WHITE);
        assertThat(black).isNotNull();
    }

    @Test
    public void move_initialMove_ok() {

        Game game = Game.singlePlayerGame();

        MoveResponse moveResponse = game.move(Colour.WHITE, new Move(new Square(Column.D, Row.TWO),
                new Square(Column.D, Row.THREE)));
        assertThat(moveResponse.getStatus()).isEqualTo(Status.OK);
    }

    // todo this test should mock an invalid move. move validation and logic should be encapsulated elsewhere
    @Test
    public void move_initialMove_invalid() {

        Game game = Game.singlePlayerGame();

        MoveResponse moveResponse = game.move(Colour.WHITE, new Move(new Square(Column.D, Row.TWO),
                new Square(Column.D, Row.FIVE)));
        assertThat(moveResponse.isInvalid()).isTrue();
    }
}
