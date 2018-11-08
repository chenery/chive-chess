package chenery.chive;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class ColumnTest {

    @Test
    public void getColumn_ok() {
        assertThat(Column.getColumn("A")).isEqualTo(Column.A);
    }

    @Test
    public void getColumn_ok_lowercase() {
        assertThat(Column.getColumn("b")).isEqualTo(Column.B);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getColumn_invalid_throwsException() {
        Column.getColumn("i");
    }

    @Test
    public void getByOrdinal_ok() {
        assertThat(Column.getByOrdinal(0)).isEqualTo(Column.A);
        assertThat(Column.getByOrdinal(7)).isEqualTo(Column.H);
    }
}
