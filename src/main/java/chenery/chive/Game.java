package chenery.chive;


import chenery.chive.MoveResponse.Status;

/**
 * TODO create a game state class?
 *
 * TODO create a command line interface
 *
 * TODO print the board after each move
 */
public class Game {

    private Board board;
    private Player white;
    private Player black;
    private GameHistory history;
    private Player playerToMoveNext;

    public Game() {
        // setup all the pieces on the board
        board = new Board();

        // setup the players
        white = new Player(Colour.WHITE);
        black = new Player(Colour.BLACK);
        playerToMoveNext = white;

        history = new GameHistory();
    }

    // todo this signature looks wrong
    // todo decide of validation during instantiation or method call)
    public MoveResponse move(Move move) {

        // valid move

        // -> correct player
        if (!move.getPlayer().equals(this.playerToMoveNext())) {
            return new MoveResponse(Status.INVALID);
        }

        // -> is there a piece at the from location?
        if (!board.getPieceAt(move.getFrom()).isPresent()) {
            return new MoveResponse(Status.INVALID);
        }

        final Piece pieceMoving = board.getPieceAt(move.getFrom()).get();

        // -> the piece at 'from' is owned by the correct player todo move this expression to a helper.
        if (!move.getPlayer().getColour().equals(pieceMoving.getColour())) {
            return new MoveResponse(Status.INVALID);
        }

        // -> the 'to' board location is a valid move for the piece
        if (!pieceMoving.canMove(move.getFrom(), move.getTo())) {
            return new MoveResponse(Status.INVALID);
        }

        // -> the 'to' board location is either vacant
        if (board.getPieceAt(move.getTo()).isPresent()) {
            // then 'to' is valid, then occupied by the other player (a capture)

        }

        // -> the move does not incur a 'check'



        // update board
        board.applyMove(move);

        // record move in history
        history.recordMove(move);

        // declare winner
        if (RuleEngine.isWinningMove(move, board)) {
            return new MoveResponse(Status.CHECKMATE);
        }

        playerToMoveNext = playerToMoveNext == white ? black : white;

        return new MoveResponse(Status.OK);
    }

    public Player playerToMoveNext() {
        return playerToMoveNext;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
