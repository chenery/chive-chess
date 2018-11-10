package chenery.chive;

import java.util.Optional;

/**
 *  todo perhaps this can provide the https://en.wikipedia.org/wiki/Algebraic_notation_(chess)?
 */
public class MoveResponse {

    public enum Status { OK, INVALID, CHECKMATE, STALEMATE, CHECK }

    private Status status;
    private String message;
    private Move move;
    private Piece pieceCaptured;

    public MoveResponse(Status status) {
        this.status = status;
    }

    public MoveResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public MoveResponse withMove(Move move) {
        this.move = move;
        return this;
    }

    public MoveResponse withPieceCaptured(Piece pieceCaptured) {
        this.pieceCaptured = pieceCaptured;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isInvalid() {
        return status == Status.INVALID;
    }

    public boolean isOK() {
        return status == Status.OK;
    }

    public Optional<Piece> getPieceCaptured() {
        return pieceCaptured != null ? Optional.of(pieceCaptured) : Optional.empty();
    }

    @Override
    public String toString() {
        return "MoveResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", move=" + move +
                ", capturedPiece=" + pieceCaptured +
                '}';
    }
}
