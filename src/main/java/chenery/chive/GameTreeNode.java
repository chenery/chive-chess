package chenery.chive;

/**
 *  An extension of a generic tree node that provides functionality for evaluating the game move tree.
 */
public interface GameTreeNode extends TreeNode<GameTreeNode> {

    int evaluateHeuristicValue();

    Move getMove();

    int getValue();

    String getNodeName();
}
