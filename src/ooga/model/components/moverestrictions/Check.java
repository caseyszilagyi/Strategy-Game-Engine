package ooga.model.components.moverestrictions;

import java.util.Set;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

import java.util.Map;


/**
 * Checks if the user will be in check after the move in question is made
 *
 * @author Casey Szilagyi
 */
public class Check extends Restriction {

  private GamePiece piece;
  private GameBoard board;
  private String thisTeamName;

  /**
   * Constructor used to hold things that many piece movement objects may need
   *
   * @param gameBoard  The board that the pieces are on.
   * @param parameters Empty in this case
   * @param piece      The piece that the restriction corresponds to
   */
  public Check(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece) {
    this.piece = piece;
    this.board = gameBoard;
  }

  /**
   * Checks if any of the opponent's pieces can take the user's king after it is moved
   *
   * @param endingCoordinates The ending coordinates of the move
   * @return True if the move is not violated by check, false otherwise
   */
  @Override
  public boolean checkRestriction(Coordinate endingCoordinates) {
    thisTeamName = piece.getPieceTeam();
    GamePiece tempRemoval = null;
    if (board.isPieceAtCoordinate(endingCoordinates)) {
      tempRemoval = board.getPieceAtCoordinate(endingCoordinates);
    }
    Boolean result = determineIfInCheck(endingCoordinates);
    if (tempRemoval != null) {
      board.addBackendPiece(tempRemoval);
    }

    return result;
  }

  // Returns true if not in check after move, false otherwise
  private boolean determineIfInCheck(Coordinate endingCoordinates) {
    Coordinate startingCoordinates = piece.getPieceCoordinates();
    board.moveBackendPiece(startingCoordinates, endingCoordinates);
    Coordinate friendlyKingLocation = board.findPieceCoordinates(thisTeamName, "king");
    Set<Coordinate> opponentTakeMoves = board
        .determineOppositeTeamTakeMovesWithoutRestrictions(thisTeamName);
    board.moveBackendPiece(endingCoordinates, startingCoordinates);
    return !opponentTakeMoves.contains(friendlyKingLocation);
  }

}
