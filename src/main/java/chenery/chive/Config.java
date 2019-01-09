package chenery.chive;

/**
 *  Static configuration for chive
 */
public class Config {

    // Move values attributed to capture of various pieces
    public static final int CAPTURE_PAWN_VALUE = 3;
    public static final int CAPTURE_ROOK_VALUE = 7;
    public static final int CAPTURE_KNIGHT_VALUE = 5;
    public static final int CAPTURE_BISHOP_VALUE = 5;
    public static final int CAPTURE_QUEEN_VALUE = 10;
    // You can't take the king, so should not impact move value, only check and checkmate should
    // This is a bit of a hack.
    public static final int CAPTURE_KING_VALUE = 0;

    public static final int CHECK_VALUE = 4;

    // Attempt to force chive to play for checkmate
    public static final int STALEMATE_VALUE = -1;
    public static final int DRAW_VALUE = -1;

    // Weight applied to value of next move
    public static final double NEXT_MOVE_FACTOR = 0.5;
}
