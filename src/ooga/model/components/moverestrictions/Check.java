package ooga.model.components.moverestrictions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import org.jetbrains.annotations.NotNull;

public class Check extends Restriction{
    /**
     * Constructor used to hold things that many piece movement objects may need
     *
     * @param viewController The controller used to communicate with the front end
     * @param gameBoard      The board that the pieces are on.
     */
    public Check(FrontEndExternalAPI viewController, GameBoard gameBoard) {
        super(viewController, gameBoard);
    }

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


    @Override
    public boolean checkRestriction() {
        return false;
    }
}
