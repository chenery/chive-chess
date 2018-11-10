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
        board = new ArrayBasedBoard().setUpAllPieces();

        // setup the players
        white = new Player(Colour.WHITE);
        black = new Player(Colour.BLACK);
        nextToMove = Colour.WHITE;

        history = new GameHistory();
        randomComputerPlayer = new RandomComputerPlayer();
    }

    public MoveResponse computerToSelectMove(Colour colour) {
        return move(colour,
                randomComputerPlayer.selectMove(colour, board));
    }

    public MoveResponse move(Colour forColour, Move move) {

        // setup a moveContext that can be used to determine the validity of the move
        MoveContext moveContext = new MoveContext(forColour, move);
        MoveResponse moveResponse = new MoveValidator().validate(move, forColour, nextToMove, board);

        if (moveResponse.isInvalid()) {
            return moveResponse;
        }

        // update board
        board.move(moveContext.getFrom(), moveContext.getTo());

        // todo if check for other player, this should be reported - also the "check" should be noted,
        // as it will impact the next turn

        // record move in history
        history.recordMove(moveContext);

        // declare winner
        if (isWinningMove(moveContext, board)) {
            return new MoveResponse(Status.CHECKMATE);
        }

        nextToMove = nextToMove == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        return moveResponse;
    }

    public void printBoard() {
        board.print();
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

    private boolean isWinningMove(MoveContext moveContext, Board board) {
        // todo decide on this
        return false;
    }
}
