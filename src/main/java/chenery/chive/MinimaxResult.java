package chenery.chive;

/**
 *
 */
public class MinimaxResult {

    // todo do not hold a reference to the node, this could be a memory leak
    private GameTreeNode gameTreeNode;
    private Integer overriddenValue;

    public MinimaxResult(GameTreeNode gameTreeNode) {
        this.gameTreeNode = gameTreeNode;
    }

    public static MinimaxResult infinity(GameTreeNode gameTreeNode) {
        return new MinimaxResult(gameTreeNode).withValue(Integer.MAX_VALUE);
    }

    public static MinimaxResult negativeInfinity(GameTreeNode gameTreeNode) {
        return new MinimaxResult(gameTreeNode).withValue(-1 * Integer.MAX_VALUE);
    }

    public MinimaxResult withValue(int value) {
        this.overriddenValue = value;
        return this;
    }

    // NOTE: if equal, a is returned
    public static MinimaxResult max(MinimaxResult a, MinimaxResult b) {
        return (a.getValue() >= b.getValue()) ? a : b;
    }

    // NOTE: if equal, a is returned
    public static MinimaxResult min(MinimaxResult a, MinimaxResult b) {
        return (a.getValue() <= b.getValue()) ? a : b;
    }

    public int getValue() {
        return overriddenValue != null ? overriddenValue : gameTreeNode.getValue();
    }

    public Move getMove() {
        return gameTreeNode.getMove();
    }

    public Board getBoard() {
        // todo
        return null;
    }
}
