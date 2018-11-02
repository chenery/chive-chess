package chenery.chive;

/**
 *  todo https://en.wikipedia.org/wiki/Portable_Game_Notation
 *  todo include the move number
 *  todo include piece captured
 *  todo include castling (king or queen side)
 */
public class Move {
    private Player player;
    private BoardLocation from;
    private BoardLocation to;

    public Move(Player player, BoardLocation from, BoardLocation to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public BoardLocation getFrom() {
        return from;
    }

    public BoardLocation getTo() {
        return to;
    }
}
