package chenery.chive;

import chenery.chive.pieces.King;
import chenery.chive.pieces.Pawn;

/**
 *  Test helper class to supply boards for testing
 */
public class BoardGenerator {

    public static Board whiteCanCheck() {
        // GIVEN valid board for which a white move checks black
        King blackKing = King.buildKing(Colour.BLACK, Square.at(Column.H, Row.EIGHT));
        Pawn blackPawn = Pawn.blackAt(Square.at(Column.G, Row.SEVEN));

        Pawn whitePawnF7 = Pawn.whiteAt(Square.at(Column.F, Row.SEVEN));
        Pawn whitePawnF6 = Pawn.whiteAt(Square.at(Column.F, Row.SIX));
        Pawn whitePawnG6 = Pawn.whiteAt(Square.at(Column.G, Row.SIX));

        // Check move: new Move(Square.at(Column.F, Row.SIX), Square.at(Column.G, Row.SEVEN))
        return new ArrayBasedBoard().setUpPieces(blackKing, blackPawn, whitePawnF7, whitePawnF6, whitePawnG6);
    }
}
