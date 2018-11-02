package chenery.chive;

/**
 *
 */
public abstract class RuleEngine {

    public static boolean moveIsAllowed(Move move, Board board, GameHistory gameHistory) {



        return true;
    }

    public static boolean isWinningMove(Move move, Board board) {
        return false;
    }
}
