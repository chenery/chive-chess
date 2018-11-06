package chenery.chive;


import chenery.chive.MoveResponse.Status;

/**
 * TODO create A game state class?
 *
 * TODO create A command line interface
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

    public void printBoard() {
        board.print();
    }

    // todo this signature looks wrong
    // todo decide of validation during instantiation or method call)
    public MoveResponse move(Colour colour, Move move) {

        // valid move
        MoveContext moveContext = new MoveContext(colour, move, history);

        // -> correct player
        if (!moveContext.getColour().equals(this.getNextToMove())) {
            return new MoveResponse(Status.INVALID).withMessage("Wrong player");
        }

        // -> is there A piece at the from location?
        if (!board.getPieceAt(moveContext.getFrom()).isPresent()) {
            return new MoveResponse(Status.INVALID).withMessage("No piece on this square");
        }

        final Piece pieceMoving = board.getPieceAt(moveContext.getFrom()).get();

        // -> the piece at 'from' is owned by the correct player todo move this expression to A helper.
        if (!moveContext.getColour().equals(pieceMoving.getColour())) {
            return new MoveResponse(Status.INVALID).withMessage("Cannot move other player's piece");
        }

        // -> the 'to' board location is A valid move for the piece
        if (!pieceMoving.canMove(moveContext)) {
            return new MoveResponse(Status.INVALID).withMessage("Piece cannot make this move");
        }

        // -> the 'to' board location is either vacant
        if (board.getPieceAt(moveContext.getTo()).isPresent()) {
            // then 'to' is valid, then occupied by the other player (A capture)

        }

        // -> the move does not incur A 'check'



        // update board
        board.applyMove(moveContext.getFrom(), moveContext.getTo());

        // record move in history
        history.recordMove(moveContext);

        // declare winner
        if (isWinningMove(moveContext, board)) {
            return new MoveResponse(Status.CHECKMATE);
        }

        nextToMove = nextToMove == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        return new MoveResponse(Status.OK).withMessage("Piece moved");
    }

    private boolean isWinningMove(MoveContext moveContext, Board board) {
        // todo decide on this
        return false;
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
