package chenery.chive;

import chenery.chive.pieces.King;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  A class that handles the "game rules"
 *
 *  todo tidy this class up
 */
public class MoveValidator {

    public MoveResponse validate(Move move, Colour byColour, Colour nextToMove, Board board) {
        return validateOptionalCheck(move, byColour, nextToMove, board, true, true);
    }

    public Set<Move> validMoves(Colour forColour, Board board) {
        Set<Move> moves = new HashSet<>();
        board.getPieces(forColour).forEach(piece -> moves.addAll(piece.potentialMoves()));

        return moves.stream()
                .filter(move -> validateOptionalCheck(move, forColour, forColour, board, true, false).isOK())
                .collect(Collectors.toSet());
    }

    private MoveResponse validateOptionalCheck(Move move, Colour byColour, Colour nextToMove, Board board,
                                          boolean testForCheck, boolean testForStaleMate) {
        // Validate correct player
        if (!byColour.equals(nextToMove)) {
            return MoveResponse.wrongPlayer();
        }

        // Validate is there a piece at the from location?
        if (!board.getPiece(move.getFrom()).isPresent()) {
            return MoveResponse.noPiece();
        }

        final Piece pieceMoving = board.getPiece(move.getFrom()).get();

        // Validate the piece at 'from' is owned by the correct player
        if (!byColour.equals(pieceMoving.getColour())) {
            return MoveResponse.wrongColour();
        }

        // Validate cannot attempt to capture own piece
        MoveContext moveContext = new MoveContext(byColour, move);
        if (board.getPiece(move.getTo()).isPresent()) {
            Piece pieceAtTo = board.getPiece(move.getTo()).get();
            if (pieceAtTo.getColour() == byColour) {
                return MoveResponse.invalidToSquare();
            }

            // it's a attempted capture, supply the piece at to location with the moveContext
            moveContext.setPieceAtLocation(pieceAtTo);
        }

        // Validate the 'to' board location is A valid move for the piece
        if (!pieceMoving.canMove(moveContext)) {
            return MoveResponse.invalidPieceMove();
        }

        // validate for any piece blocking a multiple square move (exception knight)
        if (!move.getSquaresPassed().stream()
                .filter(square -> board.getPiece(square).isPresent())
                .map(board::getPiece)
                .collect(Collectors.toSet())
                .isEmpty()) {
            return MoveResponse.invalidPieceBlocking();
        }

        if (testForCheck) {
            // test if moving player is now in 'check'
            if (isPlayerInCheck(move, Colour.otherColour(byColour), board)) {
                return MoveResponse.invalidExposeCheck();
            }

            // only check for check once here
            final boolean isPlayerInCheck = isPlayerInCheck(move, byColour, board);

            // ... as check is a precondition of checkmate
            if (isCheckMate(isPlayerInCheck, move, byColour, board)) {
                return MoveResponse.checkmate();
            }

            if (testForStaleMate && isStaleMate(isPlayerInCheck, move, byColour, board)) {
                return MoveResponse.stalemate();
            }

            // test if moving player 'checks' opponent
            if (isPlayerInCheck) {
                return MoveResponse.check();
            }
        }

        MoveResponse okResponse = MoveResponse.ok().withMove(move);
        Optional<Piece> optionalCapture = moveContext.getPieceAtToLocation();

        return optionalCapture.isPresent() ? okResponse.withPieceCaptured(optionalCapture.get()) : okResponse;
    }

    private boolean isPlayerInCheck(Move move, Colour playerWhoCanTakeKing, Board board) {

        // make the move on a new board to see if the player is now in check
        Board adjustedBoard = board.clone();
        adjustedBoard.move(move.getFrom(), move.getTo());

        // check if the "playerWhoCanTakeKing" can now capture the King
        Set<Move> followOnMoves = new HashSet<>();
        adjustedBoard.getPieces(playerWhoCanTakeKing).forEach(piece -> followOnMoves.addAll(piece.potentialMoves()));

        for (Move followOnMove : followOnMoves) {

            // Don't test for 'check' to stop unnecessary recursion
            MoveResponse moveValidation = validateOptionalCheck(
                    followOnMove, playerWhoCanTakeKing, playerWhoCanTakeKing, adjustedBoard, false, false);

            if (moveValidation.isOK() && moveValidation.getPieceCaptured().isPresent()) {
                Piece pieceCaptured = moveValidation.getPieceCaptured().get();
                if (pieceCaptured.equals(King.buildKing(Colour.otherColour(playerWhoCanTakeKing)))) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCheckMate(boolean isInCheck, Move move, Colour byColour, Board board) {

        // check is a precondition of checkmate
        if (!isInCheck) {
            return false;
        }

        // make the move
        Board adjustedBoard = board.clone();
        adjustedBoard.move(move.getFrom(), move.getTo());

        // if there are no valid moves for other colour, and in check, it's checkmate
        return validMoves(Colour.otherColour(byColour), adjustedBoard).isEmpty();
    }

    private boolean isStaleMate(boolean isInCheck, Move move, Colour byColour, Board board) {

        // no check is a precondition of stalemate
        if (isInCheck) {
            return false;
        }

        // make the move
        Board adjustedBoard = board.clone();
        adjustedBoard.move(move.getFrom(), move.getTo());

        // if there are no valid moves for other colour, and not in check, it's stalemate
        return validMoves(Colour.otherColour(byColour), adjustedBoard).isEmpty();
    }
}
