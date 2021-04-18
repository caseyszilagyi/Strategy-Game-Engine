package ooga.model.components.moverestrictions;

import java.util.List;
import java.util.Set;
import ooga.controller.FrontEndExternalAPI;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * Checks if the user will be in check after the move in question is made
 *
 * @author Casey Szilagyi
 */
public class Check extends Restriction {

  private Map<Coordinate, GamePiece> pieces;
  GamePiece piece;
  GameBoard board;
  String thisTeamName;

  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param gameBoard      The board that the pieces are on.
   * @param parameters     The map with parameters, if there are any
   * @param piece          The piece that the restriction corresponds to
   */
  public Check(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece) {
    this.piece = piece;
    this.board = gameBoard;
    thisTeamName = piece.getPieceTeam();
  }

  /**
   * Checks if any of the opponent's pieces can take the user's piece after it is moved
   * @param endingCoordinates The ending coordinates of the move
   * @return True if the move is not violated by check, false otherwise
   */
  @Override
  public boolean checkRestriction(Coordinate endingCoordinates) {
    GamePiece tempRemoval = null;
    if(board.isPieceAtCoordinate(endingCoordinates)){
       tempRemoval = board.getPieceAtCoordinate(endingCoordinates);
    }
    Coordinate startingCoordinates = piece.getPieceCoordinates();
    board.moveBackendPiece(startingCoordinates, endingCoordinates);
    Coordinate friendlyKingLocation = board.findPieceCoordinates(thisTeamName, "king");
    Set<Coordinate> opponentTakeMoves = board.determineOppositeTeamTakeMovesWithoutRestrictions(thisTeamName);
    board.moveBackendPiece(endingCoordinates, startingCoordinates);
    if(tempRemoval != null){
      board.addPiece(tempRemoval);
    }

    return !opponentTakeMoves.contains(friendlyKingLocation);
  }
}
