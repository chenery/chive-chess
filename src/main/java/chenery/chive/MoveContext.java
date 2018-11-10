package chenery.chive;

import java.util.Optional;

/**
 *  todo https://en.wikipedia.org/wiki/Portable_Game_Notation
 *  todo include the move number
 *  todo include piece captured
 *  todo include castling (king or queen side)
 */
public class MoveContext {
    private Colour colour;
    private Move move;
    private BoardLocation from;
    private BoardLocation to;
    // Null if there is no piece captured
    private Piece pieceAtToLocation;

    public MoveContext(Colour colour, Move move) {
        this.colour = colour;
        this.move = move;
        this.from = move.getFrom();
        this.to = move.getTo();
    }

    public MoveContext setPieceAtLocation(Piece pieceAtLocation) {
        this.pieceAtToLocation = pieceAtLocation;
        return this;
    }

    public int rowsMoved() {
        return Math.abs(from.getRow().ordinal() - to.getRow().ordinal());
    }

    public int columnsMoved() {
        return Math.abs(from.getColumn().ordinal() - to.getColumn().ordinal());
    }

    public RowDirection rowDirection() {
        if (from.getRow().ordinal() == to.getRow().ordinal()) {
            return RowDirection.NONE;
        }

        return from.getRow().ordinal() < to.getRow().ordinal() ? RowDirection.UP : RowDirection.DOWN;
    }

    public ColumnDirection columnDirection() {
        if (from.getColumn().ordinal() == to.getColumn().ordinal()) {
            return ColumnDirection.NONE;
        }

        return from.getColumn().ordinal() < to.getColumn().ordinal() ? ColumnDirection.RIGHT : ColumnDirection.LEFT;
    }

    public boolean isForwardMove() {
        return (this.rowDirection() == RowDirection.UP && this.getColour() == Colour.WHITE)
                || (this.rowDirection() == RowDirection.DOWN && this.getColour() == Colour.BLACK);
    }

    public Colour getColour() {
        return colour;
    }

    public BoardLocation getFrom() {
        return from;
    }

    public BoardLocation getTo() {
        return to;
    }

    public Optional<Piece> getPieceAtToLocation() {
        return pieceAtToLocation != null ? Optional.of(pieceAtToLocation) : Optional.empty();
    }

    public Move getMove() {
        return move;
    }
}
