package chenery.chive;

import java.util.Optional;

import static chenery.chive.Config.CHECK_VALUE;
import static chenery.chive.Config.DRAW_VALUE;
import static chenery.chive.Config.NEXT_MOVE_FACTOR;
import static chenery.chive.Config.STALEMATE_VALUE;
import static chenery.chive.MoveResponse.Status.CHECK;
import static chenery.chive.MoveResponse.Status.CHECKMATE;
import static chenery.chive.MoveResponse.Status.DRAW;
import static chenery.chive.MoveResponse.Status.INVALID;
import static chenery.chive.MoveResponse.Status.INVALID_EXPOSE_CHECK;
import static chenery.chive.MoveResponse.Status.INVALID_NO_PIECE;
import static chenery.chive.MoveResponse.Status.INVALID_PIECE_BLOCKING;
import static chenery.chive.MoveResponse.Status.INVALID_PIECE_MOVE;
import static chenery.chive.MoveResponse.Status.INVALID_TO_SQUARE;
import static chenery.chive.MoveResponse.Status.INVALID_WRONG_COLOUR;
import static chenery.chive.MoveResponse.Status.INVALID_WRONG_PLAYER;
import static chenery.chive.MoveResponse.Status.OK;
import static chenery.chive.MoveResponse.Status.STALEMATE;

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
        STALEMATE,
        DRAW};

    private Status status;
    private String message;
    private Move move;
    private Piece pieceCaptured;
    private MoveResponse bestOpponentRetort;
    private MoveResponse bestNextMove;
    private MoveResponse bestOpponentNextMove;

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

    public static MoveResponse stalemate() {
        return new MoveResponse(Status.STALEMATE)
                .withMessage("Stalemate!'");
    }

    public static MoveResponse drawInsufficientMaterial() {
        return new MoveResponse(Status.DRAW)
                .withMessage("Draw - insufficient material!'");
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

    public MoveResponse withBestNextMove(MoveResponse bestNextMove) {
        this.bestNextMove = bestNextMove;
        return this;
    }

    public MoveResponse withBestOpponentRetort(MoveResponse bestOpponentRetort) {
        this.bestOpponentRetort = bestOpponentRetort;
        return this;
    }

    public MoveResponse withBestOpponentNextMove(MoveResponse bestOpponentNextMove) {
        this.bestOpponentNextMove = bestOpponentNextMove;
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
                || status == CHECKMATE
                || status == STALEMATE
                || status == DRAW;
    }

    public boolean isGameOver() {
        return status == CHECKMATE
                || status == STALEMATE
                || status == DRAW;
    }

    public int getMoveValue() {

        int value = 0;

        if (status == CHECKMATE) {
            return Integer.MAX_VALUE;
        }

        if (status == STALEMATE) {
            return STALEMATE_VALUE;
        }

        if (status == DRAW) {
            return DRAW_VALUE;
        }

        if (status == CHECK) {
            value += CHECK_VALUE;
        }

        if (getPieceCaptured().isPresent()) {
            value += getPieceCaptured().get().getPieceValue();
        }

        return value;
    }

    /**
     * The value attributed to this move, the best opponent retort, and the best next move for this player
     * @return integer value for move
     */
    public int getCombinedMoveValue() {
        // The value of this move
        int combinedValue = getMoveValue();

        // minus the value of the likely response made by the opponent
        if (getBestOpponentRetort().isPresent()) {
            combinedValue -= getBestOpponentRetort().get().getMoveValue();
        }

        // plus the value from the follow-on move,
        // reduced by the NEXT_MOVE_FACTOR to represent the uncertainty of this outcome
        if (getBestNextMove().isPresent()) {
            combinedValue += NEXT_MOVE_FACTOR * getBestNextMove().get().getMoveValue();
        }

        // minus the value of the likely response made by the opponent
        if (getBestOpponentNextMove().isPresent()) {
            combinedValue -= NEXT_MOVE_FACTOR * getBestOpponentNextMove().get().getMoveValue();
        }

        return combinedValue;
    }

    public Optional<Piece> getPieceCaptured() {
        return pieceCaptured != null ? Optional.of(pieceCaptured) : Optional.empty();
    }

    public Move getMove() {
        return move;
    }

    public Optional<MoveResponse> getBestOpponentRetort() {
        return bestOpponentRetort != null ? Optional.of(bestOpponentRetort) : Optional.empty();
    }

    public Optional<MoveResponse> getBestNextMove() {
        return bestNextMove != null ? Optional.of(bestNextMove) : Optional.empty();
    }

    public Optional<MoveResponse> getBestOpponentNextMove() {
        return bestOpponentNextMove != null ? Optional.of(bestOpponentNextMove) : Optional.empty();
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
