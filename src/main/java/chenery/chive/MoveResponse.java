package chenery.chive;

/**
 *  todo perhaps this can provide the https://en.wikipedia.org/wiki/Algebraic_notation_(chess)?
 */
public class MoveResponse {

    public enum Status { OK, INVALID, CHECKMATE, STALEMATE, CHECK}

    private Status status;

    private String message;

    public MoveResponse(Status status) {
        this.status = status;
    }

    public MoveResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "MoveResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
