package chenery.chive;

/**
 * todo consider visitor pattern
 *
 *  https://en.wikipedia.org/wiki/Minimax
 *
 *  https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
 *
 *  The pseudo-code for minimax with alpha-beta pruning is as follows:[12]

     function alphabeta(node, depth, α, β, maximizingPlayer) is
         if depth = 0 or node is a terminal node then
            return the heuristic value of node

         if maximizingPlayer then
            value := −∞

            for each child of node do
                value := max(value, alphabeta(child, depth − 1, α, β, FALSE))
                α := max(α, value)
                if α ≥ β then
                    break (* β cut-off *)
            return value

         else
            value := +∞
            for each child of node do
                value := min(value, alphabeta(child, depth − 1, α, β, TRUE))
                β := min(β, value)
                if α ≥ β then
                    break (* α cut-off *)
            return value


     (* Initial call *)
     alphabeta(origin, depth, −∞, +∞, TRUE)
 */
public class Minimax {

    public static MinimaxResult minimax(GameTreeNode node, int depth, int alpha, int beta, boolean maximizingPlayer) {

        if (depth == 0 || node.isLeaf()) {
            return new MinimaxResult(node).withValue(node.evaluateHeuristicValue());
        }

        MinimaxResult value;
        if (maximizingPlayer) {
            value = MinimaxResult.negativeInfinity(node);

            for (GameTreeNode childNode : node.getChildren()) {
                value = MinimaxResult.max(value, minimax(childNode, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, value.getValue());
                if (alpha >= beta) {
                    System.out.println("Pruned branch for maximizing player at node " + node.getNodeName());
                    break;
                }
            }

            return value;

        } else { // minimizing player
            value = MinimaxResult.infinity(node);

            for (GameTreeNode childNode : node.getChildren()) {
                value = MinimaxResult.min(value, minimax(childNode, depth - 1, alpha, beta, true));
                beta = Math.min(beta, value.getValue());
                if (alpha >= beta) {
                    System.out.println("Pruned branch for minimizing player at node " + node.getNodeName());
                    break;
                }
            }

            return value;
        }

    }
}
