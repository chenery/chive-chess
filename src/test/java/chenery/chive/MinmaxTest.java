package chenery.chive;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
public class MinmaxTest {

    /**
     * This example is taking from https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
     */
    @Test
    public void minimax_with_aplhaBetaPruning() {

        // GIVEN a tree of values as per example, depth 5, 9 level 4 branches
        GameTreeNode originNode = new ChessTreeNode("1a",
            new ChessTreeNode("2a",
                new ChessTreeNode("3a",
            new ChessTreeNode("4a",
                        // depth 5, branch 1
                        new ChessTreeNode(5), new ChessTreeNode(6)
                    ),
                    new ChessTreeNode("4b",
                        // depth 5, branch 2
                        new ChessTreeNode(7), new ChessTreeNode(4), new ChessTreeNode(5)
                    )
                ),
                new ChessTreeNode("3b",
                    new ChessTreeNode("4c",
                            // depth 5, branch 3
                        new ChessTreeNode(3)
                    )
                )
            ),
            new ChessTreeNode("2b",
                new ChessTreeNode("3c",
                    new ChessTreeNode("4d",
                            // depth 5, branch 4
                        new ChessTreeNode(6)
                    ),
                    new ChessTreeNode("4e",
                        // depth 5, branch 5
                        new ChessTreeNode(6), new ChessTreeNode(9)
                    )
                ),
                new ChessTreeNode("3d",
                    new ChessTreeNode("4f",
                        // depth 5, branch 6
                        new ChessTreeNode(7)
                    )
                )
            ),
            new ChessTreeNode("2c",
                new ChessTreeNode("3e",
                    new ChessTreeNode("4g",
                        // depth 5, branch 7
                        new ChessTreeNode(5)
                    )
                ),
                new ChessTreeNode("3f",
                    new ChessTreeNode("4h",
                        // depth 5, branch 8
                        new ChessTreeNode(9), new ChessTreeNode(8)
                    ),
                    new ChessTreeNode("4i",
                            // depth 5, branch 9
                        new ChessTreeNode(6)
                    )
                )
            )
        );

        // WHEN alpha beta minimax is run
        MinimaxResult result = Minimax.minimax(originNode, 5, -1 * Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(6);
    }
}
