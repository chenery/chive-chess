package chenery.chive;

import chenery.chive.pieces.Bishop;
import chenery.chive.pieces.King;
import chenery.chive.pieces.Knight;
import chenery.chive.pieces.Pawn;
import chenery.chive.pieces.Queen;
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
        return setUpPawns(colour)
                .setUpKing(colour)
                .setUpQueen(colour)
                .setUpRooks(colour)
                .setUpKnights(colour)
                .setUpBishops(colour);
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

    public ArrayBasedBoard setUpQueen(Colour colour) {
        Square queenLocation = colour == Colour.WHITE ? WHITE_QUEEN_SQUARE : BLACK_QUEEN_SQUARE;
        return setUpPiece(new Queen(colour, queenLocation));
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

    public ArrayBasedBoard setUpBishops(Colour colour) {
        Square leftBishop = colour == Colour.WHITE ? Square.at(Column.C, Row.ONE) : Square.at(Column.F, Row.EIGHT);
        Square rightBishop = colour == Colour.WHITE ? Square.at(Column.F, Row.ONE) : Square.at(Column.C, Row.EIGHT);
        return setUpPiece(new Bishop(colour, leftBishop)).setUpPiece(new Bishop(colour, rightBishop));
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
    public boolean hasInsufficientMaterial() {

        // king against king
        List<Piece> whitePieces = getPieces(Colour.WHITE);
        List<Piece> blackPieces = getPieces(Colour.BLACK);

        if (whitePieces.size() == 1 && whitePieces.get(0).equals(King.buildKing(Colour.WHITE))
            && blackPieces.size() == 1 && blackPieces.get(0).equals(King.buildKing(Colour.BLACK))) {
            return true;
        }

        // todo https://en.wikipedia.org/wiki/Rules_of_chess#Draws
        // king against king and bishop;
        // king against king and knight;
        // king and bishop against king and bishop, with both bishops on squares of the same color

        return false;
    }

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_WHITE = "\u001B[37m";

    @Override
    public void print() {
        System.out.print("\n");
        String backgroundStr = ANSI_WHITE_BACKGROUND;

        printColumnHeader();

        for (int i = NUM_ROWS - 1; i >= 0 ; i--) {

            System.out.print(i + 1);
            System.out.print(" ");

            backgroundStr = backgroundStr.equals(ANSI_CYAN_BACKGROUND) ? ANSI_WHITE_BACKGROUND : ANSI_CYAN_BACKGROUND;
            for (int j = 0; j < NUM_COLS ; j++) {
                backgroundStr = backgroundStr.equals(ANSI_CYAN_BACKGROUND) ? ANSI_WHITE_BACKGROUND : ANSI_CYAN_BACKGROUND;
                Piece piece = board[i][j];
                System.out.print(backgroundStr + " " + getSquarePrintValue(piece) + " " + ANSI_RESET);
            }

            System.out.print(" ");
            System.out.print(i + 1);
            System.out.println();
        }

        printColumnHeader();
    }

    private void printColumnHeader() {
        System.out.print(" ");
        for (int i = 0; i < 8; i++) {
            System.out.print(" " + Column.getByOrdinal(i).get().name().toLowerCase() + " ");
        }
        System.out.println();
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

    private String getSquareVerbosePrintValue(Square atSquare, Piece piece) {
        String value = "[" + atSquare.getColumn().name() + (atSquare.getRow().ordinal() + 1) + ", ";
        value += piece != null ? piece.toString() : "_";
        return value + "]";
    }

    private String getSquarePrintValue(Piece piece) {
        return piece != null ? piece.toString() : " ";
    }
}
