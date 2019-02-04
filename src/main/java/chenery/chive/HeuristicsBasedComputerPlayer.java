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

        Set<MoveResponse> validMoveResponses = MoveValidator.validMoveResponses(colour, board, true);

        validMoveResponses.forEach(moveResponse -> {

            // Consider the best next moves for both players
            if (!moveResponse.isGameOver()) {
                Board adjustedBoard = board.clone();
                Move move = moveResponse.getMove();
                adjustedBoard.move(move.getFrom(), move.getTo());

                Set<MoveResponse> opponentValidRetorts
                        = MoveValidator.validMoveResponses(Colour.otherColour(colour), adjustedBoard, true);
                MoveResponse bestOpponentRetort = getBestMoveResponse(opponentValidRetorts);
                moveResponse.withBestOpponentRetort(bestOpponentRetort);

                if (bestOpponentRetort.getMoveValue() > 0) {
                    adjustedBoard.move(bestOpponentRetort.getMove().getFrom(), bestOpponentRetort.getMove().getTo());
                }

                Set<MoveResponse> thisPlayerValidNextMoves = MoveValidator.validMoveResponses(colour, adjustedBoard, true);
                MoveResponse bestNextMove = getBestMoveResponse(thisPlayerValidNextMoves);
                moveResponse.withBestNextMove(bestNextMove);

                adjustedBoard.move(bestNextMove.getMove().getFrom(), bestNextMove.getMove().getTo());

                Set<MoveResponse> opponentValidNextMoves
                        = MoveValidator.validMoveResponses(Colour.otherColour(colour), adjustedBoard, true);
                MoveResponse bestOpponentNextMove = getBestMoveResponse(opponentValidNextMoves);
                moveResponse.withBestOpponentNextMove(bestOpponentNextMove);

                // todo need to continue until any exchange of pieces is finished
            }
        });

        MoveResponse response = getBestMoveResponse(validMoveResponses);

        return response.getMove();
    }

    private static MoveResponse getBestMoveResponse(Set<MoveResponse> moveResponses) {
        Optional<MoveResponse> bestMoveResponse = moveResponses.stream()
                .max(Comparator.comparingInt(MoveResponse::getCombinedMoveValue));

        if (!bestMoveResponse.isPresent()) {
            throw new IllegalStateException("Cannot find move for player");
        }

        final int bestMoveValue = bestMoveResponse.get().getCombinedMoveValue();

        List<MoveResponse> possibleBestMoves = moveResponses.stream()
                .filter(moveResponse -> moveResponse.getCombinedMoveValue() == bestMoveValue)
                .collect(Collectors.toList());

        // randomly choose a move from any that are equally good.
        int randomMoveIndex = ThreadLocalRandom.current().nextInt(0, possibleBestMoves.size());
        return possibleBestMoves.get(randomMoveIndex);
    }
}
