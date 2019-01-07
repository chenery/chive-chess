package chenery.chive;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 *  Logic:
 *
 *      - review all pieces for the colour, and all their valid MoveResponses
 *      - select MoveResponse with the highest value
 *      - If any number of moves have equal value, one will be selected at random
 */
public class HeuristicsBasedComputerPlayer implements Player {

    private Colour colour;
    private Board board;

    public HeuristicsBasedComputerPlayer(Colour colour, Board board) {
        this.colour = colour;
        this.board = board;
    }

    @Override
    public Colour getColour() {
        return colour;
    }

    @Override
    public Move selectMove() {

        Set<MoveResponse> validMoveResponses = MoveValidator.validMoveResponses(colour, board);
        Optional<MoveResponse> bestMoveResponse = validMoveResponses.stream()
                .max(Comparator.comparingInt(MoveResponse::getMoveValue));

        if (!bestMoveResponse.isPresent()) {
            throw new IllegalStateException("Cannot find move for player");
        }

        final int bestMoveValue = bestMoveResponse.get().getMoveValue();

        List<Move> possibleBestMoves = validMoveResponses.stream()
                .filter(moveResponse -> moveResponse.getMoveValue() == bestMoveValue)
                .map(MoveResponse::getMove)
                .collect(Collectors.toList());

        // randomly choose a move from any that are equally good.
        int randomMoveIndex = ThreadLocalRandom.current().nextInt(0, possibleBestMoves.size());
        return possibleBestMoves.get(randomMoveIndex);
    }
}
