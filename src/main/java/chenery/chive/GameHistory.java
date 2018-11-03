package chenery.chive;

import java.util.Stack;

/**
 *
 */
public class GameHistory {

    private Stack<Move> moveHistory = new Stack<>();

    public void recordMove(Move move) {
        this.moveHistory.push(move);
    }

    public boolean isFirstMove(Colour forColour) {
        return moveHistory.size() < 2;
    }
}
