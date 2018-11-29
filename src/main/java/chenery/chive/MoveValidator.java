package chenery.chive;

import chenery.chive.MoveResponse.Status;
import chenery.chive.pieces.King;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  A class that handles the "game rules"
 *
 *  This class has been implemented more in a functional style in an effort to provide clarity.
 *  Methods are static to act as "pure functions".
 *
 *  Individual rules or validations are provided as individual methods rather than a larger method with many conditional
 *  statements. These rules must be written in the format of accepting a Context object and returning a MoveResponse.
 */
public class MoveValidator {

    public static MoveResponse validate(Move move, Colour byColour, Colour nextToMove, Board board) {
        List<Function<Context, MoveResponse>> allChecks = new ArrayList<>(VALIDATIONS);
        allChecks.addAll(GAME_STATUS_CHECKS);
        return validateRules(new Context(move, byColour, nextToMove, board), allChecks);
    }

    /**
     * All moves which the colour can move, that will not return an invalid response.
     *
     * @param forColour the colour making the move
     * @param board the "game state" defined by the board
     * @return a set of valid moves
     */
    public static Set<Move> validMoves(Colour forColour, Board board) {
        Set<Move> moves = new HashSet<>();
        board.getPieces(forColour).forEach(piece -> moves.addAll(piece.potentialMoves()));

        // We don't need to test for checkmate or stalemate to say whether a move is valid
        return moves.stream()
                .filter(move -> validateRules(new Context(move, forColour, forColour, board), VALIDATIONS).isOK())
                .collect(Collectors.toSet());
    }

    private static final List<Function<Context, MoveResponse>> VALIDATIONS = Arrays.asList(
            MoveValidator::wrongPlayer,
            MoveValidator::noPiece,
            MoveValidator::wrongColour,
            MoveValidator::invalidToSquare,
            MoveValidator::invalidPieceMove,
            MoveValidator::invalidPieceBlocking,
            MoveValidator::invalidExposeCheck);

    private static final List<Function<Context, MoveResponse>> GAME_STATUS_CHECKS = Arrays.asList(
            MoveValidator::checkmate,
            MoveValidator::stalemate);

    /**
     *
     * @param context State required to validate the move
     * @param rules Sequence of rules that constitute the rules of the game.
     *              Each rule is modelled by a function that accepts the context and returns a MoveResponse
     * @return descriptor of the response state
     */
    private static MoveResponse validateRules(Context context, List<Function<Context, MoveResponse>> rules) {

        // The list of rules allows this generic evaluate of each rule, only if the previous was OK/not invalid
        for (Function<Context, MoveResponse> rule : rules) {
            MoveResponse moveResponse = rule.apply(context);

            if (moveResponse.getStatus() != Status.OK) {
                return moveResponse;
            }
        }

        MoveResponse okResponse = MoveResponse.ok().withMove(context.getMove());
        Optional<Piece> optionalCapture = pieceCaptured(context);

        return optionalCapture.isPresent() ? okResponse.withPieceCaptured(optionalCapture.get()) : okResponse;
    }

    private static MoveResponse wrongPlayer(Context context) {
        if (!context.getByColour().equals(context.getNextToMove())) {
            return MoveResponse.wrongPlayer();
        }
        return MoveResponse.ok();
    }

    private static MoveResponse noPiece(Context context) {
        if (!context.getBoard().getPiece(context.getMove().getFrom()).isPresent()) {
            return MoveResponse.noPiece();
        }
        return MoveResponse.ok();
    }

    private static MoveResponse wrongColour(Context context) {
        final Piece pieceMoving = context.getBoard().getPiece(context.getMove().getFrom()).get();

        // Validate the piece at 'from' is owned by the correct player
        if (!context.getByColour().equals(pieceMoving.getColour())) {
            return MoveResponse.wrongColour();
        }
        return MoveResponse.ok();
    }

    private static MoveResponse invalidToSquare(Context context) {
        // Validate cannot attempt to capture own piece
        Optional<Piece> pieceCaptured = pieceCaptured(context);
        if (pieceCaptured.isPresent()) {
            Piece pieceAtTo = pieceCaptured.get();
            if (pieceAtTo.getColour() == context.getByColour()) {
                return MoveResponse.invalidToSquare();
            }
        }
        return MoveResponse.ok();
    }

    private static MoveResponse invalidPieceMove(Context context) {
        MoveContext moveContext = new MoveContext(context.getByColour(), context.getMove());
        // Validate the 'to' board location is A valid move for the piece
        final Piece pieceMoving = context.getBoard().getPiece(context.getMove().getFrom()).get();

        // moveContent will need to piece at location in order to call .canMove
        context.getBoard().getPiece(context.getMove().getTo()).ifPresent(moveContext::setPieceAtLocation);

        if (!pieceMoving.canMove(moveContext)) {
            return MoveResponse.invalidPieceMove();
        }
        return MoveResponse.ok();
    }

    private static MoveResponse invalidPieceBlocking(Context context) {
        // validate for any piece blocking a multiple square move (exception knight)
        final Board board = context.getBoard();
        if (!context.getMove().getSquaresPassed().stream()
                .filter(square -> board.getPiece(square).isPresent())
                .map(board::getPiece)
                .collect(Collectors.toSet())
                .isEmpty()) {
            return MoveResponse.invalidPieceBlocking();
        }
        return MoveResponse.ok();
    }

    private static MoveResponse invalidExposeCheck(Context context) {
        // test if moving player is now in 'check'
        if (isPlayerInCheck(context.getMove(), Colour.otherColour(context.getByColour()), context.getBoard())) {
            return MoveResponse.invalidExposeCheck();
        }
        return MoveResponse.ok();
    }

    private static MoveResponse checkmate(Context context) {
        // todo only check for 'check' once here
        final boolean isPlayerInCheck = isPlayerInCheck(context.getMove(), context.getByColour(), context.getBoard());

        // ... as check is a precondition of checkmate
        if (isCheckMate(isPlayerInCheck, context.getMove(), context.getByColour(), context.getBoard())) {
            return MoveResponse.checkmate();
        }

        // todo these could be Optional.empty()?
        return MoveResponse.ok();
    }

    private static MoveResponse stalemate(Context context) {
        // todo only check for 'check' once here
        final boolean isPlayerInCheck = isPlayerInCheck(context.getMove(), context.getByColour(), context.getBoard());

        // todo handle testForStaleMate
        if (isStaleMate(isPlayerInCheck, context.getMove(), context.getByColour(), context.getBoard())) {
            return MoveResponse.stalemate();
        }

        // test if moving player 'checks' opponent
        if (isPlayerInCheck) {
            return MoveResponse.check();
        }

        // todo these should be Optional.empty()
        return MoveResponse.ok();
    }

    private static Optional<Piece> pieceCaptured(Context context) {
        return context.getBoard().getPiece(context.getMove().getTo());
    }

    private static boolean isPlayerInCheck(Move move, Colour playerWhoCanTakeKing, Board board) {

        // make the move on a new board to see if the player is now in check
        Board adjustedBoard = board.clone();
        adjustedBoard.move(move.getFrom(), move.getTo());

        // check if the "playerWhoCanTakeKing" can now capture the King
        Set<Move> followOnMoves = new HashSet<>();
        adjustedBoard.getPieces(playerWhoCanTakeKing).forEach(piece -> followOnMoves.addAll(piece.potentialMoves()));

        for (Move followOnMove : followOnMoves) {

            MoveResponse moveValidation = validateRules(
                    new Context(followOnMove, playerWhoCanTakeKing, playerWhoCanTakeKing, adjustedBoard),
                    // These are all the VALIDATIONS, but excluding invalidExposeCheck to stop unwanted recursion
                    Arrays.asList(
                            MoveValidator::wrongPlayer,
                            MoveValidator::noPiece,
                            MoveValidator::wrongColour,
                            MoveValidator::invalidToSquare,
                            MoveValidator::invalidPieceMove,
                            MoveValidator::invalidPieceBlocking));

            if (moveValidation.isOK() && moveValidation.getPieceCaptured().isPresent()) {
                Piece pieceCaptured = moveValidation.getPieceCaptured().get();
                if (pieceCaptured.equals(King.buildKing(Colour.otherColour(playerWhoCanTakeKing)))) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isCheckMate(boolean isInCheck, Move move, Colour byColour, Board board) {

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

    private static boolean isStaleMate(boolean isInCheck, Move move, Colour byColour, Board board) {

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

    // attributes that are used to determine if the move is validate
    private static class Context {
        private Move move;
        private Colour byColour;
        private Colour nextToMove;
        private Board board;

        Context(Move move, Colour byColour, Colour nextToMove, Board board) {
            this.move = move;
            this.byColour = byColour;
            this.nextToMove = nextToMove;
            this.board = board;
        }

        public Move getMove() {
            return move;
        }

        Colour getByColour() {
            return byColour;
        }

        Colour getNextToMove() {
            return nextToMove;
        }

        public Board getBoard() {
            return board;
        }
    }
}
