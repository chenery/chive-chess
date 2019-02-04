package chenery.chive;

import java.util.Optional;

/**
 *  An extension of a generic tree node that provides functionality for evaluating the game move tree.
 */
public interface GameTreeNode extends TreeNode<GameTreeNode> {

    int evaluateHeuristicValue();

    // Optional, as the root node does not have move
    Optional<Move> getMove();

    int getValue();

    String getNodeName();
}
