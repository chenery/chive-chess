package chenery.chive;


import chenery.chive.MoveResponse.Status;

/**
 * todo allow different variants of the game, rather than just against the computer
 *
 */
public class Game {

    private Board board;
    private Player white;
    private Player black;
    private GameHistory history;
    private Colour nextToMove;
    private RandomComputerPlayer randomComputerPlayer;

    public Game() {
        // setup all the pieces on the board
        board = new Board();

        // setup the players
        white = new Player(Colour.WHITE);
        black = new Player(Colour.BLACK);
        nextToMove = Colour.WHITE;

        history = new GameHistory();
        randomComputerPlayer = new RandomComputerPlayer();
    }

    public void printBoard() {
        board.print();
    }

    public MoveResponse computerToSelectMove(Colour colour) {
        return move(colour,
                randomComputerPlayer.selectMove(colour, board));
    }

    // todo this signature looks wrong
    // todo decide of validation during instantiation or method call)
    public MoveResponse move(Colour colour, Move move) {

        // setup a moveContext that can be used to determine the validity of the move
        MoveContext moveContext = new MoveContext(colour, move);

        // Validate correct player
        if (!moveContext.getColour().equals(nextToMove)) {
            return new MoveResponse(Status.INVALID).withMessage("Wrong player");
        }

        // Validate is there a piece at the from location?
        if (!board.getPieceAt(moveContext.getFrom()).isPresent()) {
            return new MoveResponse(Status.INVALID).withMessage("No piece on this square");
        }

        final Piece pieceMoving = board.getPieceAt(moveContext.getFrom()).get();

        // Validate the piece at 'from' is owned by the correct player
        if (!moveContext.getColour().equals(pieceMoving.getColour())) {
            return new MoveResponse(Status.INVALID).withMessage("Cannot move other player's piece");
        }

        // Validate cannot attempt to capture own piece
        if (board.getPieceAt(move.getTo()).isPresent()) {
            Piece pieceAtTo = board.getPieceAt(move.getTo()).get();
            if (pieceAtTo.getColour() == colour) {
                return new MoveResponse(Status.INVALID).withMessage("Square occupied by your own piece");
            }

            // it's a attempted capture, supply the piece at to location with the moveContext
            moveContext.setPieceAtLocation(pieceAtTo);
        }

        // Validate the 'to' board location is A valid move for the piece
        if (!pieceMoving.canMove(moveContext)) {
            return new MoveResponse(Status.INVALID).withMessage("Piece cannot make this move");
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

        return new MoveResponse(Status.OK).withMessage("Piece moved").withMove(move);
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
