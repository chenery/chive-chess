package chenery.chive;


import chenery.chive.pieces.Pawn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The Board is A data structure that holds the Squares.
 *
 * A Square can hold A Piece.  The Square is a concept that should be internal to the board.
 * - todo consider making Square a static nested class in Board
 * - todo consider removing the Square, and having a fixed array board, with pieces
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
 */
public class Board {

    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    private static final int NUM_PAWNS = 8;

    // boardRows by columns
    private List<List<Square>> boardRows = new ArrayList<List<Square>>();


    public Board() {

        // add the boardRows to the board
        for (int i = 0; i < NUM_ROWS; i++) {
            // add 8 boardRows
            List<Square> rowSquares = new ArrayList<>();
            boardRows.add(rowSquares);

            // add the squares to the boardRows
            for (int j = 0; j < NUM_COLS; j++) {
                Column column = Column.getByOrdinal(j);
                // add 8 squares to the row
                Optional<Row> optionalRow = Row.getByOrdinal(i);
                optionalRow.ifPresent(row -> {
                    BoardLocation boardLocation = new BoardLocation(column, row);
                    rowSquares.add(new Square().setAtBoardLocation(boardLocation));
                });
            }
        }

        setupColour(Colour.WHITE);
        setupColour(Colour.BLACK);
    }

    // todo consider what validation is required here?
    public void applyMove(BoardLocation from, BoardLocation to) {
        Square fromSquare = getSquareAt(from);
        Optional<Piece> optionalPieceRemoved = fromSquare.removePiece();

        if (optionalPieceRemoved.isPresent()) {
            // todo validate overwriting a piece?
            Square toSquare = getSquareAt(to);
            toSquare.setPiece(optionalPieceRemoved.get());
        }
    }

    public Optional<Piece> getPieceAt(BoardLocation boardLocation) {
        Square square = getSquareAt(boardLocation);
        return square.getPiece();
    }

    /**
     *
     * @param colour
     * @return
     */
    Set<Square> getSquares(Colour colour) {
        Set<Square> squares = new HashSet<>();
        this.boardRows.forEach(row -> {
            row.forEach(square -> {
                if (square.getPiece().isPresent() && square.getPiece().get().getColour() == colour) {
                    squares.add(square);
                }
            });
        });
        return squares;
    }

    public void print() {
        System.out.print("\n");
        for (int i = NUM_ROWS - 1; i >= 0 ; i--) {
            List<Square> rowSquares = boardRows.get(i);
            for (Square square : rowSquares) {
                System.out.print(square + "\t");
            }
            System.out.println();
        }
    }

    private Square getSquareAt(BoardLocation boardLocation) {
        int boardRowIndex = boardLocation.getRow().ordinal();
        int columnIndex = boardLocation.getColumn().ordinal();
        return boardRows.get(boardRowIndex).get(columnIndex);
    }

    private void setupColour(Colour colour) {

        int rowIndex = colour == Colour.WHITE ? 1 : 6;

        // 8 pawns
        List<Square> row = boardRows.get(rowIndex);
        for (int i = 0; i < NUM_PAWNS; i++) {
            Square atSquare = row.get(i);
            atSquare.setPiece(new Pawn(colour, atSquare.getAtBoardLocation()));
        }

        // 2 rooks

        // TODO complete
    }


}
