package chenery.chive;

import java.util.*;

/**
 *
 */
public class ChessTreeNode implements GameTreeNode {

    private Integer value;
    private List<GameTreeNode> children;
    private String nodeName;
    private Board board;
    private Move move;
    private Colour playerToMove;

    public ChessTreeNode(String nodeName, GameTreeNode... children) {
        this.nodeName = nodeName;
        // todo consider more specialised list or immutability
        this.children = new ArrayList<>(Arrays.asList(children));
    }

    public ChessTreeNode(int value) {
        this.value = value;
        this.nodeName = value + "";
        // todo consider more specialised list or immutability
        this.children = new ArrayList<>();
    }

    // Constructor for a regular node with a move
    public ChessTreeNode(Board board, Colour playerToMove, Move move) {
        this.board = board;
        this.playerToMove = playerToMove;
        this.move = move;
    }

    // Constructor for the root node
    public ChessTreeNode(Board board, Colour playerToMove) {
        this.board = board;
        this.playerToMove = playerToMove;
    }

    @Override
    public int evaluateHeuristicValue() {
        // todo eval the board
        return value != null ? value : 0;
    }

    @Override
    public Optional<Move> getMove() {
        return Optional.ofNullable(move);
    }

    @Override
    public int getValue() {
        return value != null ? value : 0;
    }

    @Override
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public boolean isLeaf() {
        return getChildren().isEmpty();
    }

    // NOTE: this method is not thread safe
    @Override
    public List<GameTreeNode> getChildren() {

        if (children == null) {

            // here we build the child moves in lazy fashion

            this.children = new ArrayList<>();

            Set<MoveResponse> validMoveResponses = MoveValidator.validMoveResponses(playerToMove, board, true);

            validMoveResponses.forEach(moveResponse -> {

                // todo do we need something to stop consideration of moves after a game is over?

                Board adjustedBoard = board.clone();
                Move move = moveResponse.getMove();
                adjustedBoard.move(move.getFrom(), move.getTo());
                children.add(new ChessTreeNode(adjustedBoard, Colour.otherColour(playerToMove), move));
            });
        }

        // todo consider more specialised list or immutability
        return children;
    }
}
