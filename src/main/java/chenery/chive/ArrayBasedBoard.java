package chenery.chive;

import chenery.chive.pieces.King;
import chenery.chive.pieces.Knight;
import chenery.chive.pieces.Pawn;
import chenery.chive.pieces.Rook;

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
        return setUpPawns(colour).setUpKing(colour).setUpRooks(colour).setUpKnights(colour);
    }

    public ArrayBasedBoard setUpPawns(Colour colour) {
        int rowIndex = colour == Colour.WHITE ? 1 : 6;
        for (int i = 0; i < NUM_PAWNS; i++) {
            Square at = new Square(Column.getByOrdinal(i).get(), Row.getByOrdinal(rowIndex).get());
            setUpPiece(new Pawn(colour, at));
        }
        return this;
    }

    public ArrayBasedBoard setUpKing(Colour colour) {
        Square kingLocation = colour == Colour.WHITE ? WHITE_KING_SQUARE : BLACK_KING_SQUARE;
        return setUpPiece(new King(colour, kingLocation));
    }

    public ArrayBasedBoard setUpRooks(Colour colour) {
        Square leftRook = colour == Colour.WHITE ? Square.at(Column.A, Row.ONE) : Square.at(Column.H, Row.EIGHT);
        Square rightRook = colour == Colour.WHITE ? Square.at(Column.H, Row.ONE) : Square.at(Column.A, Row.EIGHT);
        return setUpPiece(new Rook(colour, leftRook)).setUpPiece(new Rook(colour, rightRook));
    }

    public ArrayBasedBoard setUpKnights(Colour colour) {
        Square leftKnight = colour == Colour.WHITE ? Square.at(Column.B, Row.ONE) : Square.at(Column.G, Row.EIGHT);
        Square rightRook = colour == Colour.WHITE ? Square.at(Column.G, Row.ONE) : Square.at(Column.B, Row.EIGHT);
        return setUpPiece(new Knight(colour, leftKnight)).setUpPiece(new Knight(colour, rightRook));
    }

    public ArrayBasedBoard setUpPiece(Piece piece) {

        if (getPiece(piece.getCurrentLocation()).isPresent()) {
            throw new IllegalStateException("Piece already present on this square");
        };

        board[piece.getCurrentLocation().getRow().ordinal()][piece.getCurrentLocation().getColumn().ordinal()]
                = piece;
        return this;
    }

    public ArrayBasedBoard setUpPieces(Piece... pieces) {
        Stream.of(pieces).forEach(this::setUpPiece);
        return this;
    }

    public ArrayBasedBoard setUpPiece(Piece piece, Square at) {
        board[at.getRow().ordinal()][at.getColumn().ordinal()] = piece;
        return this;
    }

    @Override
    public Optional<Piece> getPiece(Square at) {
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
    public void move(Square from, Square to) {
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
                Square atLocation = new Square(Column.getByOrdinal(j).get(), Row.getByOrdinal(i).get());
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
                                .getConstructor(Colour.class, Square.class)
                                .newInstance(piece.getColour(), piece.getOriginalLocation())
                                .setCurrentLocation(piece.getCurrentLocation());

                        clonedBoard.setUpPiece(clonedPiece);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return clonedBoard;
    }

    // todo equals and hashcode for deep comparison of boards

    private String getSquarePrintValue(Square atSquare, Piece piece) {
        String value = "[" + atSquare.getColumn().name() + (atSquare.getRow().ordinal() + 1) + ", ";
        value += piece != null ? piece.toString() : "_";
        return value + "]";
    }
}
