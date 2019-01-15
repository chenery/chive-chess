package chenery.chive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class ChessTreeNode implements GameTreeNode {

    private Integer value;
    private List<GameTreeNode> children;
    private String nodeName;

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

    @Override
    public int evaluateHeuristicValue() {
        // todo eval the board
        return value != null ? value : 0;
    }

    @Override
    public Move getMove() {
        return null;
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
        return children.isEmpty();
    }

    @Override
    public List<GameTreeNode> getChildren() {
        // todo consider more specialised list or immutability
        return children;
    }
}
