package chenery.chive;

/**
 *
 */
public class MoveResponse {

    public enum Status { OK, INVALID, CHECKMATE, STALEMATE, CHECK}

    private Status status;

    public MoveResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
