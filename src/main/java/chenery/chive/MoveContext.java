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
    private Square from;
    private Square to;
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

    public Colour getColour() {
        return colour;
    }

    public Square getFrom() {
        return from;
    }

    public Square getTo() {
        return to;
    }

    public Optional<Piece> getPieceAtToLocation() {
        return pieceAtToLocation != null ? Optional.of(pieceAtToLocation) : Optional.empty();
    }

    public Move getMove() {
        return move;
    }
}
