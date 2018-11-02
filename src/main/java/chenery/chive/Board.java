package chenery.chive;


import chenery.chive.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *  ROWS x COLS
 *  8 boardSquares and 8 columns
 *
 *                      BLACK
 *          a       b       c           h
 *          col 0, col 1, col 2, ... col 7
 *  8 row: 7
 *  7 row: 6
 *  ...
 *  2 row 1
 *  1 row 0
 *
 *                      WHITE
 */
public class Board {

    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    private static final int NUM_PAWNS = 8;

    // boardSquares by columns
    private List<List<Square>> boardSquares = new ArrayList<List<Square>>();


    public Board() {

        // add the boardSquares to the board
        for (int i = 0; i < NUM_ROWS; i++) {
            // add 8 boardSquares
            List<Square> rowSquares = new ArrayList<>();
            boardSquares.add(rowSquares);

            // add the squares to the boardSquares
            for (int j = 0; j < NUM_COLS; j++) {
                // add 8 squares to the row
                rowSquares.add(new Square());
            }
        }

        setupColour(Colour.WHITE);
        setupColour(Colour.BLACK);
    }

    public void applyMove(Move move) {

    }

    public Optional<Piece> getPieceAt(BoardLocation boardLocation) {
        int boardRowIndex = boardLocation.getRow().ordinal();
        int columnIndex = boardLocation.getColumn().ordinal();
        Square square = boardSquares.get(boardRowIndex).get(columnIndex);
        return square.getPiece();
    }

    private void setupColour(Colour colour) {

        int rowIndex = colour == Colour.WHITE ? 1 : 6;

        // 8 pawns
        List<Square> row = boardSquares.get(rowIndex);
        for (int i = 0; i < NUM_PAWNS; i++) {
            row.get(i).withPiece(new Pawn().setColour(colour));
        }

        // 2 rooks

        // TODO complete
        System.out.println("Completed setup of " + colour);
    }


}
