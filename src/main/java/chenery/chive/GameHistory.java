package chenery.chive;

import java.util.Stack;

/**
 *
 */
public class GameHistory {

    private Stack<MoveContext> moveContextHistory = new Stack<>();

    public void recordMove(MoveContext moveContext) {
        this.moveContextHistory.push(moveContext);
    }

    public boolean isFirstMove(Colour forColour) {
        return moveContextHistory.size() < 2;
    }
}
