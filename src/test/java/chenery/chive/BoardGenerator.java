package chenery.chive;

import chenery.chive.pieces.King;
import chenery.chive.pieces.Pawn;
import chenery.chive.pieces.Rook;

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

    public static Board whiteCheckMateKingRook() {
        King blackKing = King.blackAt(Square.at(Column.D, Row.EIGHT));

        King whiteKing = King.whiteAt(Square.at(Column.D, Row.SIX));
        Rook whiteRook = Rook.whiteAt(Square.at(Column.G, Row.EIGHT));

        return new ArrayBasedBoard().setUpPieces(blackKing, whiteKing, whiteRook);
    }
}
