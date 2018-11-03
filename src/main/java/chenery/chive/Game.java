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
    private Colour nextToMove;

    public Game() {
        // setup all the pieces on the board
        board = new Board();

        // setup the players
        white = new Player(Colour.WHITE);
        black = new Player(Colour.BLACK);
        nextToMove = Colour.WHITE;

        history = new GameHistory();
    }

    // todo this signature looks wrong
    // todo decide of validation during instantiation or method call)
    public MoveResponse move(Colour colour, BoardLocation from, BoardLocation to) {

        // valid move
        Move move = new Move(colour, from, to, history);

        // -> correct player
        if (!move.getColour().equals(this.getNextToMove())) {
            return new MoveResponse(Status.INVALID);
        }

        // -> is there a piece at the from location?
        if (!board.getPieceAt(move.getFrom()).isPresent()) {
            return new MoveResponse(Status.INVALID);
        }

        final Piece pieceMoving = board.getPieceAt(move.getFrom()).get();

        // -> the piece at 'from' is owned by the correct player todo move this expression to a helper.
        if (!move.getColour().equals(pieceMoving.getColour())) {
            return new MoveResponse(Status.INVALID);
        }

        // -> the 'to' board location is a valid move for the piece
        if (!pieceMoving.canMove(move)) {
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

        nextToMove = nextToMove == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        return new MoveResponse(Status.OK);
    }

    public Colour getNextToMove() {
        return nextToMove;
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
