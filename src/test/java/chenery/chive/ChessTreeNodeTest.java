package chenery.chive;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class ChessTreeNodeTest {

    @Test
    public void isLeaf_false() {

        // GIVEN a new game
        Board board = new ArrayBasedBoard().setUpAllPieces();

        // WHEN create a new tree for the new board
        ChessTreeNode node = new ChessTreeNode(board, Colour.WHITE);

        // THEN the root node is correctly identified as not a leaf
        assertThat(node.isLeaf()).isFalse();
    }

    @Test
    public void isLeaf_true() {

        // GIVEN board that is a checkmate position
        Board board = BoardGenerator.whiteCheckMateKingRook();

        // WHEN create a new tree for the board
        ChessTreeNode node = new ChessTreeNode(board, Colour.WHITE);

        // THEN the root node is correctly identified as a leaf
        assertThat(node.isLeaf()).isTrue();
    }

    @Test
    public void getChildren_lazyBuilds() {

        // GIVEN a new game
        Board board = new ArrayBasedBoard().setUpAllPieces();

        // WHEN create a new tree for the new board
        ChessTreeNode node = new ChessTreeNode(board, Colour.WHITE);

        // THEN there are 20 retorts for black to make for a new game
        List<GameTreeNode> children = node.getChildren();
        assertThat(children).isNotNull().hasSize(20);
    }
}
