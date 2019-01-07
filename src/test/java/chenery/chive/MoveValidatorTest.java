package chenery.chive;

import chenery.chive.pieces.Bishop;
import chenery.chive.pieces.King;
import chenery.chive.pieces.Knight;
import chenery.chive.pieces.Pawn;
import chenery.chive.pieces.Queen;
import chenery.chive.pieces.Rook;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static chenery.chive.Board.BLACK_KING_SQUARE;
import static chenery.chive.Board.WHITE_KING_SQUARE;
import static chenery.chive.Config.CAPTURE_QUEEN_VALUE;
import static chenery.chive.MoveResponse.Status.CHECK;
import static chenery.chive.MoveResponse.Status.CHECKMATE;
import static chenery.chive.MoveResponse.Status.DRAW;
import static chenery.chive.MoveResponse.Status.INVALID_EXPOSE_CHECK;
import static chenery.chive.MoveResponse.Status.INVALID_NO_PIECE;
import static chenery.chive.MoveResponse.Status.INVALID_PIECE_BLOCKING;
import static chenery.chive.MoveResponse.Status.INVALID_PIECE_MOVE;
import static chenery.chive.MoveResponse.Status.INVALID_TO_SQUARE;
import static chenery.chive.MoveResponse.Status.INVALID_WRONG_COLOUR;
import static chenery.chive.MoveResponse.Status.INVALID_WRONG_PLAYER;
import static chenery.chive.MoveResponse.Status.OK;
import static chenery.chive.MoveResponse.Status.STALEMATE;
import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class MoveValidatorTest {

    @Test
    public void validate_ok() {

        // GIVEN valid board and move
        Board board = new ArrayBasedBoard().setUpAllPieces();
        Move move = new Move(Square.at(Column.A, Row.TWO), Square.at(Column.A, Row.FOUR));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN ok
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(OK);
    }

    @Test
    public void validate_wrongPlayer() {

        // GIVEN any board and move
        Board board = new ArrayBasedBoard().setUpAllPieces();
        Move move = new Move(Square.at(Column.A, Row.TWO), Square.at(Column.A, Row.FOUR));

        // WHEN validate wrong player
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.BLACK, board);

        // THEN invalid wrong player
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_WRONG_PLAYER);
    }

    @Test
    public void validate_noPiece() {

        // GIVEN any board and move targetting no piece
        Board board = new ArrayBasedBoard().setUpAllPieces();
        Move move = new Move(Square.at(Column.A, Row.THREE), Square.at(Column.A, Row.FIVE));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_NO_PIECE);
    }

    @Test
    public void validate_wrongColour() {

        // GIVEN valid board and move but move is targetting the wrong colour (black pawn)
        Board board = new ArrayBasedBoard().setUpAllPieces();
        Move move = new Move(Square.at(Column.A, Row.SEVEN), Square.at(Column.A, Row.FIVE));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_WRONG_COLOUR);
    }

    @Test
    public void validate_invalidToSquare() {

        // GIVEN valid board and move that would be OK except players piece already exists there
        Pawn a2Pawn = new Pawn(Colour.WHITE, Square.at(Column.A, Row.TWO));
        Pawn a3Pawn = new Pawn(Colour.WHITE, Square.at(Column.A, Row.THREE));
        Board board = new ArrayBasedBoard().setUpPiece(a2Pawn).setUpPiece(a3Pawn);
        Move move = new Move(Square.at(Column.A, Row.TWO), Square.at(Column.A, Row.THREE));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_TO_SQUARE);
    }

    @Test
    public void validate_invalidPieceMove() {

        // GIVEN valid board and move that is invalid for the piece
        Pawn a2Pawn = new Pawn(Colour.WHITE, Square.at(Column.A, Row.TWO));
        Board board = new ArrayBasedBoard().setUpPiece(a2Pawn);
        Move move = new Move(Square.at(Column.A, Row.TWO), Square.at(Column.A, Row.FIVE));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_PIECE_MOVE);
    }

    @Test
    public void validate_invalidPieceBlocking_ownPawn() {

        // GIVEN a board for which a pawn is immediately blocked by piece
        Pawn a2Pawn = new Pawn(Colour.WHITE, Square.at(Column.A, Row.TWO));
        Pawn a3Pawn = new Pawn(Colour.WHITE, Square.at(Column.A, Row.THREE));

        // AND a move that would be valid, but is blocked
        Board board = new ArrayBasedBoard().setUpPieces(a2Pawn, a3Pawn);
        Move move = new Move(Square.at(Column.A, Row.TWO), Square.at(Column.A, Row.FOUR));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_PIECE_BLOCKING);
    }

    @Test
    public void validate_invalidPieceBlocking_rook_opposingPawn() {

        // GIVEN a board for which a rook is immediately blocked by pawn
        Rook rook = new Rook(Colour.WHITE, Square.at(Column.A, Row.ONE));
        Pawn pawn = new Pawn(Colour.BLACK, Square.at(Column.A, Row.SEVEN));

        // AND a move that would be valid, but is blocked
        Board board = new ArrayBasedBoard().setUpPieces(rook, pawn);
        Move move = new Move(Square.at(Column.A, Row.ONE), Square.at(Column.A, Row.EIGHT));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_PIECE_BLOCKING);
    }

    // NOTE: this test is an exceptional case for invalidPieceBlocking
    @Test
    public void validate_ok_knight_opposingPawn() {

        // GIVEN a board for which a knight passes 'over' pawn
        Knight knight = Knight.whiteAt(Square.at(Column.B, Row.ONE));
        Pawn b2Pawn = Pawn.whiteAt(Square.at(Column.B, Row.TWO));
        Pawn c2Pawn = Pawn.whiteAt(Square.at(Column.C, Row.TWO));

        // AND a move that would be valid, but is blocked
        Board board = new ArrayBasedBoard().setUpKing(Colour.BLACK).setUpPieces(knight, b2Pawn, c2Pawn);

        Move move = new Move(Square.at(Column.B, Row.ONE), Square.at(Column.C, Row.THREE));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN ok
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(OK);
    }

    @Test
    public void validate_invalidExposeCheck() {

        // GIVEN valid board that when moving a king would expose a check from a pawn
        King blackKing = new King(Colour.BLACK, BLACK_KING_SQUARE);
        Pawn whiteD6Pawn = new Pawn(Colour.WHITE, Square.at(Column.D, Row.SIX));
        Board board = new ArrayBasedBoard().setUpPiece(whiteD6Pawn).setUpPiece(blackKing);
        Move move = new Move(BLACK_KING_SQUARE, Square.at(Column.E, Row.SEVEN));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.BLACK, Colour.BLACK, board);

        // THEN invalid
        assertThat(response.isInvalid()).isTrue();
        assertThat(response.getStatus()).isEqualTo(INVALID_EXPOSE_CHECK);
    }

    @Test
    public void validate_checkOpponent() {

        // GIVEN valid board that when moving a pawn would check the king
        King blackKing = new King(Colour.BLACK, BLACK_KING_SQUARE);
        Pawn whiteD6Pawn = new Pawn(Colour.WHITE, Square.at(Column.D, Row.SIX));
        Board board = new ArrayBasedBoard().setUpPiece(whiteD6Pawn).setUpPiece(blackKing);
        Move move = new Move(Square.at(Column.D, Row.SIX), Square.at(Column.D, Row.SEVEN));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN invalid
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(CHECK);
    }

    @Test
    public void validate_check_multiplePieces() {

        // GIVEN valid board for which a white move checks black
        King blackKing = King.buildKing(Colour.BLACK, Square.at(Column.H, Row.EIGHT));
        Pawn blackPawn = Pawn.blackAt(Square.at(Column.G, Row.SEVEN));

        Pawn whitePawnF7 = Pawn.whiteAt(Square.at(Column.F, Row.SEVEN));
        Pawn whitePawnF6 = Pawn.whiteAt(Square.at(Column.F, Row.SIX));
        Pawn whitePawnG6 = Pawn.whiteAt(Square.at(Column.G, Row.SIX));

        Board board = new ArrayBasedBoard().setUpPieces(blackKing, blackPawn, whitePawnF7, whitePawnF6, whitePawnG6);
        Move move = new Move(Square.at(Column.F, Row.SIX), Square.at(Column.G, Row.SEVEN));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN check
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(CHECK);
    }

    @Test
    public void validate_checkmate_withKingAndRook() {

        // GIVEN valid board for which a white move checkmates black
        King blackKing = King.blackAt(Square.at(Column.D, Row.EIGHT));

        King whiteKing = King.whiteAt(Square.at(Column.D, Row.SIX));
        Rook whiteRook = Rook.whiteAt(Square.at(Column.G, Row.SEVEN));

        Board board = new ArrayBasedBoard().setUpPieces(blackKing, whiteKing, whiteRook);
        Move move = new Move(Square.at(Column.G, Row.SEVEN), Square.at(Column.G, Row.EIGHT));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN check
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(CHECKMATE);
    }

    @Test
    public void validate_checkmate_withFromRealGame() {

        // GIVEN real board
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        Rook.whiteAt(Square.at(Column.A, Row.ONE)),
                        Knight.whiteAt(Square.at(Column.B, Row.ONE)),
                        Bishop.whiteAt(Square.at(Column.C, Row.ONE)),
                        King.whiteAt(Square.at(Column.E, Row.ONE)),
                        Knight.whiteAt(Square.at(Column.G, Row.ONE)),
                        Pawn.whiteAt(Square.at(Column.A, Row.TWO)),
                        Pawn.whiteAt(Square.at(Column.B, Row.TWO)),
                        Pawn.whiteAt(Square.at(Column.C, Row.TWO)),
                        Pawn.whiteAt(Square.at(Column.F, Row.TWO)),
                        Pawn.whiteAt(Square.at(Column.G, Row.TWO)),
                        Pawn.whiteAt(Square.at(Column.E, Row.THREE)),
                        Pawn.whiteAt(Square.at(Column.D, Row.FIVE)),
                        Bishop.whiteAt(Square.at(Column.C, Row.SIX)),
                        Queen.whiteAt(Square.at(Column.E, Row.SEVEN)),
                        Rook.whiteAt(Square.at(Column.E, Row.EIGHT)),
                        Pawn.blackAt(Square.at(Column.A, Row.THREE)),
                        Pawn.blackAt(Square.at(Column.C, Row.FIVE)),
                        Pawn.blackAt(Square.at(Column.F, Row.FIVE)),
                        Pawn.blackAt(Square.at(Column.B, Row.SIX)),
                        King.blackAt(Square.at(Column.C, Row.SEVEN)),
                        Bishop.blackAt(Square.at(Column.D, Row.SEVEN)),
                        Rook.blackAt(Square.at(Column.A, Row.EIGHT)));

        // AND a move for white that should be checkmate
        Move move = new Move(Square.at(Column.E, Row.SEVEN), Square.at(Column.D, Row.SEVEN));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN check
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(CHECKMATE);
    }

    @Test
    public void validate_staleMate() {

        // GIVEN stalemate move as per https://en.wikipedia.org/wiki/Stalemate
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        King.whiteAt(Square.at(Column.F, Row.SEVEN)),
                        Queen.whiteAt(Square.at(Column.F, Row.SIX)),
                        King.blackAt(Square.at(Column.H, Row.EIGHT)));

        // AND a move for white that should be checkmate
        Move move = new Move(Square.at(Column.F, Row.SIX), Square.at(Column.G, Row.SIX));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN check
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(STALEMATE);
    }

    @Test
    public void validate_drawInsufficientMaterial_kings() {

        // GIVEN board with insufficient material https://en.wikipedia.org/wiki/Rules_of_chess#Draws
        Board board = new ArrayBasedBoard()
                .setUpKing(Colour.WHITE)
                .setUpKing(Colour.BLACK);

        // AND a move for white that would normally be valid
        Move move = new Move(WHITE_KING_SQUARE, Square.at(Column.E, Row.TWO));

        // WHEN validate
        MoveResponse response = MoveValidator.validate(move, Colour.WHITE, Colour.WHITE, board);

        // THEN check
        assertThat(response.isOK()).isTrue();
        assertThat(response.getStatus()).isEqualTo(DRAW);
    }

    @Test
    public void validMoves_forBoardWithOnlyKing() {

        // GIVEN a board with just a king
        Board board = new ArrayBasedBoard().setUpKing(Colour.WHITE);

        // WHEN get valid moves for this board for black player
        Set<Move> validMoves = MoveValidator.validMoves(Colour.WHITE, board);

        // THEN there should be 6 possible moves
        assertThat(validMoves).hasSize(5);
    }

    @Test
    public void validMoveResponses_moveValues_zeroValues() {

        // GIVEN new game board
        Board board = new ArrayBasedBoard().setUpAllPieces();

        // WHEN valid move responses for White
        Set<MoveResponse> moveResponses = MoveValidator.validMoveResponses(Colour.WHITE, board);

        // THEN Can move pawns and knights
        assertThat(moveResponses).hasSize(20);

        // AND all moves have zero value
        moveResponses.forEach(moveResponse -> assertThat(moveResponse.getMoveValue()).isZero());
    }

    @Test
    public void validMoveResponses_moveValues_capturePiece() {

        // GIVEN board for which WHITE can capture black queen
        Board board = new ArrayBasedBoard()
                .setUpPieces(
                        King.black(),
                        Queen.black(),
                        King.white(),
                        Queen.whiteAt(Square.at(Column.D, Row.FIVE)));

        // WHEN valid move responses for White
        Set<MoveResponse> moveResponses = MoveValidator.validMoveResponses(Colour.WHITE, board);
        assertThat(moveResponses).hasSize(32);

        // THEN all moves without captures have zero value, but capture has correct value
        moveResponses.stream()
                .filter(moveResponse -> !moveResponse.getPieceCaptured().isPresent())
                .forEach(moveResponse -> assertThat(moveResponse.getMoveValue()).isZero());

        Set<MoveResponse> captureResponses = moveResponses
                .stream().filter(moveResponse -> moveResponse.getPieceCaptured().isPresent())
                .collect(Collectors.toSet());

        assertThat(captureResponses).hasSize(1);
        assertThat(captureResponses.iterator().next().getMoveValue()).isEqualTo(CAPTURE_QUEEN_VALUE);
    }
}
