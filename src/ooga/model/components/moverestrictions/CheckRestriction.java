package ooga.model.components.moverestrictions;

import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import org.jetbrains.annotations.NotNull;

public class CheckRestriction extends Restriction{
    public boolean placesOpponentInCheck(GameBoard board, Coordinate pieceCoordinate, String currentPlayer){
        String player = getOppositePlayer(currentPlayer);
        Coordinate kingLocation = board.findKing(player);
        return false;
    }

    @NotNull
    private String getOppositePlayer(String currentPlayer) {
        String player = (currentPlayer == "user") ? "opponent" : "user";
        return player;
    }


}
