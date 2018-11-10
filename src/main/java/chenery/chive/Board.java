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

    BoardLocation WHITE_KING_LOCATION = new BoardLocation(Column.E, Row.ONE);
    BoardLocation BLACK_KING_LOCATION = new BoardLocation(Column.E, Row.EIGHT);

    Optional<Piece> getPiece(BoardLocation at);

    List<Piece> getPieces(Colour forColour);

    void move(BoardLocation from, BoardLocation to);

    void print();

    Board clone();
}
