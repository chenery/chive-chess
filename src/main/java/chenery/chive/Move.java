package chenery.chive;

/**
 *
 */
public class Move {

    private BoardLocation from;
    private BoardLocation to;

    public Move(BoardLocation from, BoardLocation to) {
        this.from = from;
        this.to = to;
    }

    public BoardLocation getFrom() {
        return from;
    }

    public BoardLocation getTo() {
        return to;
    }
}
