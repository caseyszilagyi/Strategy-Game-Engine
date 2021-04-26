package ooga.model.components.moverestrictions;

import java.util.List;
import java.util.Map;
import ooga.model.components.movehistory.ActionType;
import ooga.model.components.movehistory.CompletedAction;
import ooga.model.components.Coordinate;
import ooga.model.components.GameBoard;
import ooga.model.components.GamePiece;

/**
 * The en passant movement in chess. Allows a pawn to take another pawn in passing, only
 * right after the other pawn moves and if its first move is two spaces forward
 *
 * @author Casey Szilagyi
 */
public class EnPassant extends Restriction{

  private GameBoard gameBoard;
  private GamePiece pawn;


  /**
   * Has the board that will need to be referred to in order to see if the move is valid
   * @param gameBoard The board that the piece is on
   * @param parameters Empty for this restriction
   * @param piece The pawn that we are checking to see if it can move
   */
  public EnPassant(GameBoard gameBoard, Map<String, String> parameters, GamePiece piece){
    this.gameBoard = gameBoard;
    pawn = piece;

  }

  /**
   * Checks if the en-passant movement is valid
   * @param endingCoordinates The ending coordinates of the move
   * @return True if it is, false otherwise
   */
  @Override
  public boolean checkRestriction(Coordinate endingCoordinates) {
    GamePiece takePawn = findTakePawn(endingCoordinates);
    return checkPawnFirstMoveForward2(takePawn) && checkBoardMostRecentMove(endingCoordinates, takePawn.getPieceCoordinates());
  }

  // Finds the piece at the take location
  private GamePiece findTakePawn(Coordinate endingCoordinates){
    Coordinate currentMovingPawnCoordinates = pawn.getPieceCoordinates();
    int necessaryPawnX = endingCoordinates.getX();
    int necessaryPawnY = currentMovingPawnCoordinates.getY();
    Coordinate necessaryPawnLocation = new Coordinate(necessaryPawnX, necessaryPawnY);
    return gameBoard.getPieceAtCoordinate(necessaryPawnLocation);
  }

  // Checks to see if the piece that we are taking is a pawn and that it has just moved forward by two
  private boolean checkPawnFirstMoveForward2(GamePiece takePawn){
    List<Coordinate> takePawnHistory = takePawn.getLocationHistory();
    return takePawn.getPieceName().equals("pawn") &&
        takePawnHistory.size() == 2 &&
        Math.abs(takePawnHistory.get(0).getY() - takePawnHistory.get(1).getY()) == 2;
  }

  // Checks to see that the most recent move is the correct pawn
  private boolean checkBoardMostRecentMove(Coordinate endingCoordinates, Coordinate takePawnCoordinates){
    CompletedAction mostRecentMove = gameBoard.getMostRecentAction();
    return mostRecentMove.actionType() == ActionType.MOVE &&
        mostRecentMove.correspondingPiece().getPieceName().equals("pawn") &&
        mostRecentMove.coords()[1].equals(takePawnCoordinates);
  }



}
