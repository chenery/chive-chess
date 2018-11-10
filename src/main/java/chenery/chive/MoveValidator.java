package chenery.chive;

import chenery.chive.MoveResponse.Status;
import chenery.chive.pieces.King;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static chenery.chive.Board.BLACK_KING_LOCATION;
import static chenery.chive.Board.WHITE_KING_LOCATION;

/**
 *
 */
public class MoveValidator {

    public MoveResponse validate(Move move, Colour byColour, Colour nextToMove, Board board) {
        return validateOptionalCheck(move, byColour, nextToMove, board, true);
    }

    public Set<Move> validMoves(Colour forColour, Board board) {
        Set<Move> moves = new HashSet<>();
        board.getPieces(forColour).forEach(piece -> moves.addAll(piece.potentialMoves()));

        return moves.stream()
                .filter(move -> validate(move, forColour, forColour, board).isOK())
                .collect(Collectors.toSet());
    }

    private MoveResponse validateOptionalCheck(Move move, Colour byColour, Colour nextToMove, Board board,
                                          boolean testForCheck) {
        // Validate correct player
        if (!byColour.equals(nextToMove)) {
            return new MoveResponse(Status.INVALID).withMessage("Wrong player");
        }

        // Validate is there a piece at the from location?
        if (!board.getPiece(move.getFrom()).isPresent()) {
            return new MoveResponse(Status.INVALID).withMessage("No piece on this square");
        }

        final Piece pieceMoving = board.getPiece(move.getFrom()).get();

        // Validate the piece at 'from' is owned by the correct player
        if (!byColour.equals(pieceMoving.getColour())) {
            return new MoveResponse(Status.INVALID).withMessage("Cannot move other player's piece");
        }

        // Validate cannot attempt to capture own piece
        MoveContext moveContext = new MoveContext(byColour, move);
        if (board.getPiece(move.getTo()).isPresent()) {
            Piece pieceAtTo = board.getPiece(move.getTo()).get();
            if (pieceAtTo.getColour() == byColour) {
                return new MoveResponse(Status.INVALID).withMessage("Square occupied by your own piece");
            }

            // it's a attempted capture, supply the piece at to location with the moveContext
            moveContext.setPieceAtLocation(pieceAtTo);
        }

        // Validate the 'to' board location is A valid move for the piece
        if (!pieceMoving.canMove(moveContext)) {
            return new MoveResponse(Status.INVALID).withMessage("Piece cannot make this move");
        }

        if (testForCheck && isPlayerInCheck(move, byColour, board)) {
            return new MoveResponse(Status.INVALID).withMessage("Move would put King in 'check'");
        }

        MoveResponse okResponse = new MoveResponse(Status.OK).withMessage("Piece moved");
        Optional<Piece> optionalCapture = moveContext.getPieceAtToLocation();

        return optionalCapture.isPresent() ? okResponse.withPieceCaptured(optionalCapture.get()) : okResponse;
    }

    private boolean isPlayerInCheck(Move move, Colour byColour, Board board) {

        // make the move on a new board to see if the player is now in check
        Board adjustedBoard = board.clone();
        adjustedBoard.move(move.getFrom(), move.getTo());

        Colour otherColour = Colour.otherColour(byColour);
        Set<Move> followOnMoves = new HashSet<>();
        board.getPieces(otherColour).forEach(piece -> followOnMoves.addAll(piece.potentialMoves()));

        for (Move followOnMove : followOnMoves) {

            // Don't test for 'check' to stop unnecessary recursion
            MoveResponse moveValidation = validateOptionalCheck(
                    followOnMove, otherColour, otherColour, adjustedBoard, false);

            if (moveValidation.isOK() && moveValidation.getPieceCaptured().isPresent()) {
                Piece pieceCaptured = moveValidation.getPieceCaptured().get();
                BoardLocation kingLocation = byColour == Colour.WHITE ? WHITE_KING_LOCATION : BLACK_KING_LOCATION;
                if (pieceCaptured.equals(new King(byColour, kingLocation))) {
                    return true;
                }
            }
        }

        return false;
    }
}
