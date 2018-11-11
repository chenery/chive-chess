package chenery.chive;

import org.junit.Test;

import static chenery.chive.Row.ONE;

/**
 *
 */
public class SquareTest {

    @Test
    public void construct_colIndexRange_ok() {
        new Square(Column.A, ONE);
    }
}
