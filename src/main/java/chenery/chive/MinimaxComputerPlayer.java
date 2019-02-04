package chenery.chive;

/**
 *  Logic:
 *
 *      Conceptual change:
 *
 * - No longer consider move value - but assign a value to boards for each player
 * - So the heuristic function will simply accept board and player arguments, rather than board, player and move.
 *
 * Algorithm to select a move:
 *
 * - From the current position, build a move tree for all possible future moves up to a depth of d
 * - The move tree will store the move and the resultant board
 * - Execute the minimax algorithm with alpha beta pruning over the tree to work out which board position at depth d
 * (or less if a terminal node) results in the best 'board' for the player
 */
public class MinimaxComputerPlayer implements Player {

    private Colour colour;
    private Board board;

    private static final int MINIMAX_SEARCH_DEPTH = 3;
    private static final int MINIMAX_ALPHA = -1 * Integer.MAX_VALUE;
    private static final int MINIMAX_BETA = Integer.MAX_VALUE;

    public MinimaxComputerPlayer(Colour colour, Board board) {
        this.colour = colour;
        this.board = board;
    }

    @Override
    public Colour getColour() {
        return colour;
    }

    @Override
    public Move selectMove() {
        GameTreeNode rootNode = new ChessTreeNode(board, colour);
        MinimaxResult result = Minimax.minimax(rootNode, MINIMAX_SEARCH_DEPTH, MINIMAX_ALPHA, MINIMAX_BETA, true);
        return result.getMove();
    }
}
