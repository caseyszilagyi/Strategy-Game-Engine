package ooga.model.components.moverestrictions;

import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class Check extends Restriction {
    private Map<Coordinate, GamePiece> pieces;

    /**
     * Constructor used to hold things that many piece movement objects may need
     *
     * @param viewController The controller used to communicate with the front end
     * @param gameBoard      The board that the pieces are on.
     * @param parameters     The map with parameters, if there are any
     * @param piece          The piece that the restriction corresponds to
     */
    public Check(FrontEndExternalAPI viewController, GameBoard gameBoard, Map<String, String> parameters, GamePiece piece) {
        super(viewController, gameBoard, parameters, piece);
    }


    /**
     * Determines if any of the player's pieces can "take" the opponent's king, i.e. if
     * the possible moves of a player's piece overlaps with the position of the enemy king
     *
     * @param board         current board
     * @param currentPlayer player who is checking the opponent
     * @return boolean to determine if opposing player has been put in check
     */
    public boolean opponentInCheck(GameBoard board, String currentPlayer) {
        Coordinate kingLocation = board.findKing(getOppositePlayer(currentPlayer));
        for (GamePiece piece : getPiecesByTeam(board, currentPlayer).values()) {
            for (Coordinate coord : piece.determineAllLegalMoves()) {
                return (coord == kingLocation);
            }
        }
        return false;
    }

    private Map<Coordinate, GamePiece> getPiecesByTeam(GameBoard board, String currentPlayer) {
        for (GamePiece piece : board.getPieceCoordMap().values()) {
            if (piece.getPieceTeam().equals(currentPlayer)) pieces.put(piece.getPieceCoordinates(), piece);
        }
        return pieces;
    }

    @NotNull
    private String getOppositePlayer(String currentPlayer) {
        return (currentPlayer.equals("user")) ? "opponent" : "user";
    }

    @Override
    public boolean checkRestriction(Coordinate endingCoordinates) {
        return false;
    }
}
