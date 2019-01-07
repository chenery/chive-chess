package chenery.chive;


/**
 *
 */
public class Game {

    private Board board;
    private Player white;
    private Player black;
    private GameHistory history;
    private Colour nextToMove;

    private Game(Player white, Player black, Board board) {
        this.white = white;
        this.black = black;
        this.board = board;
        nextToMove = Colour.WHITE;
        history = new GameHistory();
    }

    public static Game singlePlayerGame() {
        Board board = new ArrayBasedBoard().setUpAllPieces();
        return new Game(new UserPlayer(Colour.WHITE), new HeuristicsBasedComputerPlayer(Colour.BLACK, board), board);
    }

    public static Game twoComputersGame() {
        Board board = new ArrayBasedBoard().setUpAllPieces();
        return new Game(new HeuristicsBasedComputerPlayer(Colour.WHITE, board),
                new RandomComputerPlayer(Colour.BLACK, board), board);
    }

    public MoveResponse move(Colour forColour, Move move) {

        // setup a moveContext that can be used to determine the validity of the move
        MoveContext moveContext = new MoveContext(forColour, move);
        MoveResponse moveResponse = MoveValidator.validate(move, forColour, nextToMove, board);

        if (moveResponse.isInvalid()) {
            return moveResponse;
        }

        // update board
        board.move(moveContext.getFrom(), moveContext.getTo());

        // record move in history
        history.recordMove(moveContext);

        nextToMove = nextToMove == Colour.WHITE ? Colour.BLACK : Colour.WHITE;

        return moveResponse;
    }

    public void printBoard() {
        board.print();
    }

    public Colour getNextToMove() {
        return nextToMove;
    }

    public Player getNextPlayerToMove() {
        return getNextToMove() == Colour.WHITE ? getWhite() : getBlack();
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public GameHistory getHistory() {
        return history;
    }
}
