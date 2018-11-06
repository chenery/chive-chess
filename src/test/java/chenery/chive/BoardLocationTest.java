package chenery.chive;

import org.junit.Test;

import static chenery.chive.Row.ONE;

/**
 *
 */
public class BoardLocationTest {

    @Test
    public void construct_colIndexRange_ok() {
        new BoardLocation(ONE, Column.A);
    }
}
