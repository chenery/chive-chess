package chenery.chive;

/**
 *
 */
public class MinimaxResult {

    private Move move;
    private Integer overriddenValue;
    private int value = 0;

    public MinimaxResult(GameTreeNode gameTreeNode) {
        // todo stuff out unchecked get()
        this.move = gameTreeNode.getMove().get();
        this.value = gameTreeNode.getValue();
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
        return overriddenValue != null ? overriddenValue : this.value;
    }

    public Move getMove() {
        return this.move;
    }
}
