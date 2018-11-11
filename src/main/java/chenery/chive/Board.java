package chenery.chive;


import java.util.List;
import java.util.Optional;

/**
 * The Board is A data structure that holds the Squares/Pieces.
 *
 * The Board does not contain any of the game/piece logic/rules of the game.
 *
 *
 *  ROWS x COLS
 *  8 boardRows and 8 columns
 *
 *                      BLACK
 *          A       B       C           H
 *          col 0, col 1, col 2, ... col 7
 *  8 row: 7
 *  7 row: 6
 *  ...
 *  2 row 1
 *  1 row 0
 *
 *                      WHITE
 *
 */
public interface Board extends Cloneable {

    Square WHITE_KING_SQUARE = new Square(Column.E, Row.ONE);
    Square BLACK_KING_SQUARE = new Square(Column.E, Row.EIGHT);

    Optional<Piece> getPiece(Square at);

    List<Piece> getPieces(Colour forColour);

    void move(Square from, Square to);

    void print();

    Board clone();
}
