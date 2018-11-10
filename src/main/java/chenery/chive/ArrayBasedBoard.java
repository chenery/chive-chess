package chenery.chive;

import chenery.chive.pieces.King;
import chenery.chive.pieces.Pawn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class ArrayBasedBoard implements Board {

    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 8;
    private static final int NUM_PAWNS = 8;

    // row x columns
    private Piece[][] board = new Piece[8][8];

    public ArrayBasedBoard setUpAllPieces() {
        return setUpColour(Colour.WHITE).setUpColour(Colour.BLACK);
    }

    public ArrayBasedBoard setUpColour(Colour colour) {
        return setUpPawns(colour).setUpKing(colour);
    }

    public ArrayBasedBoard setUpPawns(Colour colour) {
        int rowIndex = colour == Colour.WHITE ? 1 : 6;
        for (int i = 0; i < NUM_PAWNS; i++) {
            BoardLocation at = new BoardLocation(Column.getByOrdinal(i).get(), Row.getByOrdinal(rowIndex).get());
            setUpPiece(new Pawn(colour, at), at);
        }
        return this;
    }

    public ArrayBasedBoard setUpKing(Colour colour) {
        BoardLocation kingLocation = colour == Colour.WHITE ? WHITE_KING_LOCATION : BLACK_KING_LOCATION;
        return setUpPiece(new King(colour, kingLocation), kingLocation);
    }

    public ArrayBasedBoard setUpPiece(Piece piece, BoardLocation at) {
        board[at.getRow().ordinal()][at.getColumn().ordinal()] = piece;
        return this;
    }

    @Override
    public Optional<Piece> getPiece(BoardLocation at) {
        Piece pieceAt = board[at.getRow().ordinal()][at.getColumn().ordinal()];
        return pieceAt != null ? Optional.of(pieceAt) : Optional.empty();
    }

    @Override
    public List<Piece> getPieces(Colour forColour) {
        return Stream.of(board)
                .flatMap(Stream::of)
                .filter(piece -> piece != null && piece.getColour() == forColour)
                .collect(Collectors.toList());
    }

    @Override
    public void move(BoardLocation from, BoardLocation to) {
        getPiece(from).ifPresent(piece -> {
            // remove piece from location
            board[from.getRow().ordinal()][from.getColumn().ordinal()] = null;
            // to location
            // todo record captured pieces
            board[to.getRow().ordinal()][to.getColumn().ordinal()] = piece;
            piece.setCurrentLocation(to);
        });
    }

    @Override
    public void print() {
        System.out.print("\n");
        for (int i = NUM_ROWS - 1; i >= 0 ; i--) {
            for (int j = 0; j < NUM_COLS ; j++) {
                Piece piece = board[i][j];
                BoardLocation atLocation = new BoardLocation(Column.getByOrdinal(j).get(), Row.getByOrdinal(i).get());
                System.out.print(getSquarePrintValue(atLocation, piece) + "\t");
            }
            System.out.println();
        }
    }

    @Override
    public Board clone() {
        // A deep copy of the board
        ArrayBasedBoard clonedBoard = new ArrayBasedBoard();

        for (int i = NUM_ROWS - 1; i >= 0 ; i--) {
            for (int j = 0; j < NUM_COLS; j++) {
                Piece piece = board[i][j];

                if (piece != null) {
                    try {
                        // Note: this works as long as Colour and BoardLocation are immutable
                        Piece clonedPiece = piece.getClass()
                                .getConstructor(Colour.class, BoardLocation.class)
                                .newInstance(piece.getColour(), piece.getOriginalLocation());

                        BoardLocation at = new BoardLocation(Column.getByOrdinal(j).get(), Row.getByOrdinal(i).get());
                        clonedBoard.setUpPiece(clonedPiece, at);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return clonedBoard;
    }

    // todo equals and hashcode for deep comparison of boards

    private String getSquarePrintValue(BoardLocation atBoardLocation, Piece piece) {
        String value = "[" + atBoardLocation.getColumn().name() + (atBoardLocation.getRow().ordinal() + 1) + ", ";
        value += piece != null ? piece.toString() : "__";
        return value + "]";
    }
}
