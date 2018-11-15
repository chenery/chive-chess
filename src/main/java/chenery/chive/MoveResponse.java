package chenery.chive;

import java.util.Optional;

import static chenery.chive.MoveResponse.Status.CHECK;
import static chenery.chive.MoveResponse.Status.CHECKMATE;
import static chenery.chive.MoveResponse.Status.INVALID;
import static chenery.chive.MoveResponse.Status.INVALID_EXPOSE_CHECK;
import static chenery.chive.MoveResponse.Status.INVALID_NO_PIECE;
import static chenery.chive.MoveResponse.Status.INVALID_PIECE_BLOCKING;
import static chenery.chive.MoveResponse.Status.INVALID_PIECE_MOVE;
import static chenery.chive.MoveResponse.Status.INVALID_TO_SQUARE;
import static chenery.chive.MoveResponse.Status.INVALID_WRONG_COLOUR;
import static chenery.chive.MoveResponse.Status.INVALID_WRONG_PLAYER;
import static chenery.chive.MoveResponse.Status.OK;

/**
 *  todo perhaps this can provide the https://en.wikipedia.org/wiki/Algebraic_notation_(chess)?
 */
public class MoveResponse {

    public enum Status {
        OK,
        INVALID,
        INVALID_WRONG_PLAYER,
        INVALID_NO_PIECE,
        INVALID_WRONG_COLOUR,
        INVALID_TO_SQUARE,
        INVALID_PIECE_MOVE,
        INVALID_PIECE_BLOCKING,
        INVALID_EXPOSE_CHECK,
        CHECK,
        CHECKMATE,
        STALEMATE };

    private Status status;
    private String message;
    private Move move;
    private Piece pieceCaptured;

    public MoveResponse(Status status) {
        this.status = status;
    }

    public static MoveResponse ok() {
        return new MoveResponse(OK).withMessage("Piece moved");
    }

    public static MoveResponse wrongPlayer() {
        return new MoveResponse(INVALID_WRONG_PLAYER).withMessage("Wrong player");
    }

    public static MoveResponse noPiece() {
        return new MoveResponse(INVALID_NO_PIECE).withMessage("No piece on this square");
    }

    public static MoveResponse wrongColour() {
        return new MoveResponse(Status.INVALID_WRONG_COLOUR).withMessage("Cannot move other player's piece");
    }

    public static MoveResponse invalidToSquare() {
        return new MoveResponse(Status.INVALID_TO_SQUARE)
                .withMessage("Square occupied by your own piece");
    }

    public static MoveResponse invalidPieceMove() {
        return new MoveResponse(Status.INVALID_PIECE_MOVE)
                .withMessage("Piece cannot make this move");
    }

    public static MoveResponse invalidExposeCheck() {
        return new MoveResponse(Status.INVALID_EXPOSE_CHECK)
                .withMessage("Move would put King in 'check'");
    }

    public static MoveResponse invalidPieceBlocking() {
        return new MoveResponse(Status.INVALID_PIECE_BLOCKING)
                .withMessage("Another piece is blocking this move");
    }

    public static MoveResponse check() {
        return new MoveResponse(Status.CHECK)
                .withMessage("Opponent is now in 'check'");
    }

    public static MoveResponse checkmate() {
        return new MoveResponse(Status.CHECKMATE)
                .withMessage("Checkmate!'");
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
        return status == INVALID
                || status == INVALID_NO_PIECE
                || status == INVALID_WRONG_PLAYER
                || status == INVALID_WRONG_COLOUR
                || status == INVALID_TO_SQUARE
                || status == INVALID_PIECE_MOVE
                || status == INVALID_PIECE_BLOCKING
                || status == INVALID_EXPOSE_CHECK;
    }

    public boolean isOK() {
        return status == OK
                || status == CHECK
                || status == CHECKMATE;
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
