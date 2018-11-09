package chenery.chive;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class ColumnTest {

    @Test
    public void getColumn_ok() {
        assertThat(Column.getColumn("A").get()).isEqualTo(Column.A);
    }

    @Test
    public void getColumn_ok_lowercase() {
        assertThat(Column.getColumn("b").get()).isEqualTo(Column.B);
    }

    @Test
    public void getColumn_invalid() {
        assertThat(Column.getColumn("i").isPresent()).isFalse();
    }

    @Test
    public void getByOrdinal_ok() {
        assertThat(Column.getByOrdinal(0).get()).isEqualTo(Column.A);
        assertThat(Column.getByOrdinal(7).get()).isEqualTo(Column.H);
    }
}
